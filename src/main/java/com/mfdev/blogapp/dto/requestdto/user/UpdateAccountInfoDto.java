package com.mfdev.blogapp.dto.requestdto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UpdateAccountInfoDto {
  private Long id;
  private String email;
  private String newUsername;
  private String oldPassword;
  private String newPassword;
  private String profileImage;
  private String firstName;
  private String lastName;
  private String profileDescription;
}
