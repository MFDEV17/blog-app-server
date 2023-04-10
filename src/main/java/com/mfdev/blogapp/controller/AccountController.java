package com.mfdev.blogapp.controller;

import com.mfdev.blogapp.dto.requestdto.user.RegistrationDto;
import com.mfdev.blogapp.dto.requestdto.user.UpdateAccountInfoDto;
import com.mfdev.blogapp.service.user.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@EnableAsync
@RequiredArgsConstructor
@RequestMapping("/api/v1/account")
public class AccountController {
  private final AccountService userService;

  @PostMapping
  public ResponseEntity<?> register(@RequestBody RegistrationDto dto) {
    return userService.createUser(dto);
  }

  @GetMapping("/{uuid}")
  public ResponseEntity<?> activateAccount(@PathVariable UUID uuid) {
    return userService.activateAccount(uuid);
  }

  @PutMapping
  public ResponseEntity<?> updateAccountInfo(@RequestBody UpdateAccountInfoDto dto) {
    return userService.updateAccountInfo(dto);
  }

  @DeleteMapping("/{userId}")
  public ResponseEntity<?> deleteAccount(@PathVariable Long userId) {
    return userService.deleteAccount(userId);
  }
}
