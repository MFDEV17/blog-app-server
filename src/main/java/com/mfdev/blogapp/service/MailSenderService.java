package com.mfdev.blogapp.service;

import com.mfdev.blogapp.entity.VerificationCode;
import com.mfdev.blogapp.entity.user.User;
import com.mfdev.blogapp.repository.verificationcode.VerificationCodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class MailSenderService {
  private final JavaMailSender mailSender;
  private final VerificationCodeRepository codeRepository;
  private final TaskExecutor taskExecutor;

  private final String MAIL_SENDER_ADDRESS = System.getenv("MAIL_SENDER_ADDRESS");

  @Async
  public CompletableFuture<ResponseEntity<String>> sendVerificationMail(User user) {
    SimpleMailMessage message = new SimpleMailMessage();

    UUID uuid = UUID.randomUUID();

    message.setText(setMessageToSend(user, uuid));
    message.setFrom(MAIL_SENDER_ADDRESS);
    String SUBJECT_TITLE = "Account verification";
    message.setSubject(SUBJECT_TITLE);
    message.setTo(user.getEmail());

    taskExecutor.execute(() ->
            mailSender.send(message));

    VerificationCode verificationCode = new VerificationCode(uuid, user);
    this.codeRepository.save(verificationCode);

    return CompletableFuture
            .completedFuture(setSuccessSendMailMessage(user));
  }

  private String setMessageToSend(User user, UUID uuid) {
    final String verificationLink =
            String.format("http://localhost:8080/verify/%s", uuid);

    return String.format(
            "Hi, %s!\nClick the link to verify your account: \n%s",
            user.getUsername(), verificationLink
    );
  }

  private ResponseEntity<String> setSuccessSendMailMessage(User user) {
    return ResponseEntity.ok(
            String.format(
                    "You're almost done!\nWe sent a verification mail to %s",
                    user.getEmail()
            )
    );
  }
}

