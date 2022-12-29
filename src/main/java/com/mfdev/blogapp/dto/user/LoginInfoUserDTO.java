package com.mfdev.blogapp.dto.user;

import com.mfdev.blogapp.entity.user.authority.Role;

public interface LoginInfoUserDTO {
  String getUsername();
  Boolean getIsEnable();
  String getPassword();
  Role getRole();
}
