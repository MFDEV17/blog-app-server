package com.mfdev.blogapp.dto.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateUserInfoDTO {
  private Long username;
  private String email;
  private String imageUrl;
  private String oldPassword;
  private String newPassword;
}
