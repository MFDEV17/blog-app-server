package com.mfdev.blogapp.entity.user;

import com.mfdev.blogapp.entity.user.authority.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;

import java.util.Date;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.GenerationType.IDENTITY;

@Entity(name = "users")
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = {"id", "username"})
@DynamicUpdate
@ToString(of = {"id", "username", "email"})
public class User {
  @Id
  @GeneratedValue(strategy = IDENTITY)
  private Long id;

  @Column(unique = true, nullable = false)
  private String username;

  @Column(unique = true, nullable = false)
  @Email(
          message = "Invalid email",
          regexp = "^\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*(\\.\\w{2,3})+$"
  )
  private String email;

  @Column(nullable = false)
  @Size(
          min = 8,
          max = 90,
          message = "Min size of password is 8 characters"
  )
  private String password;

  @Column(nullable = false)
  private Boolean isEnable = true;

  private String profileImage;

  @CreationTimestamp
  private Date dateCreate;

  private String firstName;

  private String lastName;

  private String profileDescription;

  @Enumerated(value = STRING)
  @Column(nullable = false)
  private Role role = Role.USER;

  public User(String username, String email, String password) {
    this.username = username;
    this.email = email;
    this.password = password;
  }

  public User(String username, String email, String password, Boolean isEnable, String firstName, String lastName, Role role) {
    this.username = username;
    this.email = email;
    this.password = password;
    this.isEnable = isEnable;
    this.firstName = firstName;
    this.lastName = lastName;
    this.role = role;
  }

  public User(String username, String email, String password, Boolean isEnable, Role role) {
    this.username = username;
    this.email = email;
    this.password = password;
    this.isEnable = isEnable;
    this.role = role;
  }
}