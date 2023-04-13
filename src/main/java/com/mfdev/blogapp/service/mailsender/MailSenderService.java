package com.mfdev.blogapp.service.mailsender;

import com.mfdev.blogapp.entity.user.User;
import com.mfdev.blogapp.entity.verificationcode.VerificationCode;
import com.mfdev.blogapp.repository.verificationcode.VerificationCodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MailSenderService {
  private final JavaMailSender mailSender;
  private final VerificationCodeRepository verificationCodeRepository;

  @Value("${spring.mail.username}")
  private String MAIL_SENDER_ADDRESS;

  @Async
  public void sendVerificationMail(User user) {
    SimpleMailMessage message = new SimpleMailMessage();

    UUID uuid = UUID.randomUUID();

    message.setText(setMessageToSend(user, uuid));
    message.setFrom(MAIL_SENDER_ADDRESS);
    String SUBJECT_TITLE = "Account verification";
    message.setSubject(SUBJECT_TITLE);
    message.setTo(user.getEmail());

    mailSender.send(message);

    VerificationCode verificationCode = new VerificationCode(uuid, user);
    verificationCodeRepository.save(verificationCode);
  }

  private String setMessageToSend(User user, UUID uuid) {
    final String verificationLink =
            String.format("http://localhost:8080/api/v1/account/%s", uuid);

    return String.format(
            "Hi, %s!\nClick the link to verify your account: \n%s",
            user.getUsername(), verificationLink
    );
  }

  public ResponseEntity<String> generateSuccessSendMailMessage(User user) {
    return ResponseEntity.ok(
            String.format(
                    "You're almost done!\nWe sent a verification mail to %s",
                    user.getEmail()
            )
    );
  }
}

