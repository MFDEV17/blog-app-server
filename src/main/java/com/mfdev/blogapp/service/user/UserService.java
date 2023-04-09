package com.mfdev.blogapp.service.user;

import com.mfdev.blogapp.dto.user.RegistrationDto;
import com.mfdev.blogapp.entity.user.User;
import com.mfdev.blogapp.mapper.user.UserMapper;
import com.mfdev.blogapp.repository.user.UserRepository;
import com.mfdev.blogapp.repository.verificationcode.VerificationCodeRepository;
import com.mfdev.blogapp.service.mailsender.MailSenderService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static com.mfdev.blogapp.util.ExceptionUtil.uniqueExceptionHandler;
import static java.util.Objects.requireNonNull;

@Service
@RequiredArgsConstructor
public class UserService {
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
    }

    return mailService.generateSuccessSendMailMessage(user);
  }

  public ResponseEntity<?> activateAccount(UUID uuid) {
    User user = verificationCodeRepository.findUserByVerificationCode(uuid)
            .orElseThrow(() -> new RuntimeException("Not found"));

    user.setIsEnable(true);
    userRepository.save(user);

    return ResponseEntity.ok("Account has been activated!");
  }
}
