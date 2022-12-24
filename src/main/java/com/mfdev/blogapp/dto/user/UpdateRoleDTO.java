package com.mfdev.blogapp.dto.user;

import com.mfdev.blogapp.entity.user.authority.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateRoleDTO {
  private Role role;
  private String username;
}
