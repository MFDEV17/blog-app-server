package com.mfdev.blogapp.dto.requestdto.user;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RegistrationDto {
  private String username;
  private String email;
  private String password;
  private String profileImage;
  private String firstName;
  private String lastName;
  private String profileDescription;
}
