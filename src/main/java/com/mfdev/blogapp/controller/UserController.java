package com.mfdev.blogapp.controller;

import com.mfdev.blogapp.dto.user.RegistrationDto;
import com.mfdev.blogapp.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@EnableAsync
@RequiredArgsConstructor
public class UserController {
  private final UserService userService;

  @PostMapping("/register")
  public ResponseEntity<?> register(@RequestBody RegistrationDto dto) {
    return userService.createUser(dto);
  }

  @GetMapping("/verify/{uuid}")
  public ResponseEntity<?> activateAccount(@PathVariable UUID uuid) {
    return userService.activateAccount(uuid);
  }
}
