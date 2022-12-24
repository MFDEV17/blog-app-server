package com.mfdev.blogapp.dto.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateUserPasswordDTO {
  private String oldPassword;
  private String newPassword;
}
