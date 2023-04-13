package com.mfdev.blogapp.service.account;

import com.mfdev.blogapp.dto.requestdto.user.RegistrationDto;
import com.mfdev.blogapp.dto.requestdto.user.UpdateAccountInfoDto;
import com.mfdev.blogapp.entity.user.User;
import com.mfdev.blogapp.mapper.user.UserMapper;
import com.mfdev.blogapp.repository.user.UserRepository;
import com.mfdev.blogapp.repository.verificationcode.VerificationCodeRepository;
import com.mfdev.blogapp.service.mailsender.MailSenderService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static com.mfdev.blogapp.util.ExceptionUtil.uniqueExceptionHandler;
import static java.lang.String.format;
import static java.util.Objects.requireNonNull;

@Service
@RequiredArgsConstructor
public class AccountService {
  private final UserRepository userRepository;
  private final VerificationCodeRepository verificationCodeRepository;
  private final UserMapper userMapper;
  private final MailSenderService mailService;

  public ResponseEntity<?> createUser(RegistrationDto dto) {
    final User user;

    try {
      user = userRepository.save(userMapper.registrationDtoToUser(dto));
      mailService.sendVerificationMail(user);
    } catch (DataIntegrityViolationException e) {
      return uniqueExceptionHandler(e);
    } catch (MailException e) {
      return ResponseEntity.badRequest().body(requireNonNull(e.getRootCause()).getMessage());
    } catch (ConstraintViolationException e) {
      return ResponseEntity.badRequest().body("Invalid mail");
    }

    return mailService.generateSuccessSendMailMessage(user);
  }

  public ResponseEntity<?> activateAccount(UUID uuid) {
    userRepository.activateAccount(uuid);
    verificationCodeRepository.deleteVerificationCode(uuid);

    return ResponseEntity.ok("Account has been activated!");
  }

  public ResponseEntity<?> updateAccountInfo(UpdateAccountInfoDto dto) {
    if (dto.getNewPassword() != null && dto.getOldPassword() != null) {
      if (!dto.getNewPassword().equals(dto.getOldPassword())) {
        return ResponseEntity.badRequest().body("Passwords don't equals");
      }
    }

    if (dto.getNewPassword() != null && dto.getOldPassword() == null) {
      return ResponseEntity.badRequest().body("Old password required");
    }

    if (dto.getOldPassword() != null && dto.getNewPassword() == null) {
      return ResponseEntity.badRequest().body("New password required");
    }

    try {
      User toUpdate = userRepository.getReferenceById(dto.getId());
      userMapper.updateUserEntityByUpdateAccountInfoDto(toUpdate, dto);
      userRepository.save(toUpdate);
    } catch (DataIntegrityViolationException e) {
      return uniqueExceptionHandler(e);
    } catch (EntityNotFoundException e) {
      return ResponseEntity.badRequest().body(format("User with id %d were not found", dto.getId()));
    }

    return ResponseEntity.ok("Account info has been updated");
  }

  public ResponseEntity<?> deleteAccount(Long userId) {
    userRepository.deleteAccount(userId);
    return ResponseEntity.ok("Account has been deleted");
  }
}
