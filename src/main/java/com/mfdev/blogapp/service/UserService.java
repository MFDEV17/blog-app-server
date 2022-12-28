package com.mfdev.blogapp.service;

import com.mfdev.blogapp.dto.user.UpdateUserPasswordDTO;
import com.mfdev.blogapp.entity.user.authority.Role;
import com.mfdev.blogapp.entity.user.User;
import com.mfdev.blogapp.repository.UserRepository;
import com.mfdev.blogapp.repository.VerificationCodeRepository;
import com.mfdev.blogapp.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static com.mfdev.blogapp.util.ExceptionUtil.uniqueExceptionHandler;

@Service
@RequiredArgsConstructor
public class UserService {
  private final SecurityUtil securityUtil;
  private final UserRepository userRepository;
  private final MailSenderService mailSender;
  private final PasswordEncoder encoder;
  private final VerificationCodeRepository verificationCodeRepository;

  @SneakyThrows
  @PreAuthorize("permitAll()")
  public ResponseEntity<?> createUser(User user) {
    try {
      user.setPassword(encoder.encode(user.getPassword()));
      userRepository.save(user);
    } catch (DataIntegrityViolationException e) {
      return uniqueExceptionHandler(e);
    }

    return mailSender.sendVerificationMail(user).get();
  }

  @PreAuthorize("permitAll()")
  public ResponseEntity<?> activateAccount(UUID uuid) {
    User user = verificationCodeRepository.findUserByVerificationCode(uuid)
            .orElseThrow(() -> new UsernameNotFoundException("Not found"));

    user.setIsEnable(true);
    userRepository.save(user);

    return ResponseEntity.ok("Account has been activated!");
  }

  @PreAuthorize("authentication.name.equals(#username) or " +
          "hasAuthority('SCOPE_DELETE_OTHER_ACCOUNT')")
  public ResponseEntity<?> deleteUser(String username) {
    userRepository.deleteByUsername(username);
    return ResponseEntity.ok("User has been deleted");
  }

  @PreAuthorize("hasAuthority('SCOPE_BAN_USER')")
  public ResponseEntity<?> banUser(String username) {
    userRepository.banUser(username);
    return ResponseEntity.ok("User has been banned.");
  }

  @PreAuthorize("hasAnyAuthority('SCOPE_SET_USER_AUTHORITY')")
  public ResponseEntity<?> setUserRole(Role role, String username) {
    userRepository.updateRole(role, username);
    return ResponseEntity.ok("User permission has been updated.");
  }

  @PreAuthorize("isFullyAuthenticated()")
  public ResponseEntity<?> editPassword(UpdateUserPasswordDTO dto) {
    String actualOldPassword = userRepository
            .getUserPassword(securityUtil.getSessionUsername());

    if (encoder.matches(dto.getOldPassword(), actualOldPassword)) {
      String newPassword = encoder.encode(dto.getNewPassword());

      userRepository.updatePassword(
              newPassword,
              securityUtil.getSessionUsername()
      );
      return ResponseEntity.ok("Password has been updated");
    }

    return ResponseEntity.badRequest().body("Passwords don't matches");
  }

  @PreAuthorize("isFullyAuthenticated()")
  public ResponseEntity<?> editEmail(String email) {
    try {
      userRepository.updateUserEmail(
              email,
              securityUtil.getSessionUsername()
      );
    } catch (DataIntegrityViolationException e) {
      return uniqueExceptionHandler(e);
    }

    return ResponseEntity.ok("Email has been updated");
  }
}
