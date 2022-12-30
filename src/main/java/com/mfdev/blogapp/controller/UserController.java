package com.mfdev.blogapp.controller;

import com.mfdev.blogapp.dto.user.UpdateRoleDTO;
import com.mfdev.blogapp.dto.user.UpdateUserPasswordDTO;
import com.mfdev.blogapp.entity.user.User;
import com.mfdev.blogapp.service.jwt.JwtGenerationService;
import com.mfdev.blogapp.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@EnableAsync
@RequiredArgsConstructor
public class UserController {
  private final UserService userService;
  private final JwtGenerationService jwtGenerationService;

  @PostMapping("/login")
  public String login(Authentication auth) {
    return jwtGenerationService.generateToken(auth);
  }

  @PostMapping("/register")
  public ResponseEntity<?> register(@RequestBody User user) {
    return userService.createUser(user);
  }

  @GetMapping("/verify/{uuid}")
  public ResponseEntity<?> activateAccount(@PathVariable UUID uuid) {
    return userService.activateAccount(uuid);
  }

  @DeleteMapping("/delete/{username}")
  public ResponseEntity<?> deleteAccount(@PathVariable String username) {
    return userService.deleteUser(username);
  }

  @PutMapping("/ban/{username}")
  public ResponseEntity<?> banAccount(@PathVariable String username) {
    return userService.banUser(username);
  }

  @PutMapping("/set-role")
  public ResponseEntity<?> setUserRole(@RequestBody UpdateRoleDTO dto) {
    return userService.setUserRole(dto.getRole(), dto.getUsername());
  }

  @PutMapping("/update-password")
  public ResponseEntity<?> updateUserPassword(@RequestBody UpdateUserPasswordDTO dto) {
    return userService.editPassword(dto);
  }

  @PutMapping("/update-email/{email}")
  public ResponseEntity<?> updateEmail(@PathVariable String email) {
    return userService.editEmail(email);
  }
}
