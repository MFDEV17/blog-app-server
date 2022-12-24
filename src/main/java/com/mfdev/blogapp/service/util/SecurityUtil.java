package com.mfdev.blogapp.service.util;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class SecurityUtil {
  public String getSessionUsername() {
    return SecurityContextHolder.getContext().getAuthentication().getName();
  }
}
