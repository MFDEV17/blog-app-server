package com.mfdev.blogapp.util;

import com.mfdev.blogapp.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SecurityUtil {
  private final UserRepository userRepository;

  public String getSessionUsername() {
    return SecurityContextHolder
            .getContext()
            .getAuthentication()
            .getName();
  }

  public Long getSessionUserId() {
    String username = SecurityContextHolder
            .getContext()
            .getAuthentication()
            .getName();
    return userRepository.getUserId(username);
  }
}
