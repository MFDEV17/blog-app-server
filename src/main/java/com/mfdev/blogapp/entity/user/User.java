package com.mfdev.blogapp.entity.user;

import com.mfdev.blogapp.entity.baseentities.BaseEntity;
import com.mfdev.blogapp.entity.user.authority.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@DynamicUpdate
@ToString(of = {"username", "email"})
public class User extends BaseEntity {

  @Column(unique = true, nullable = false)
  private String username;

  @Column(unique = true, nullable = false)
  @Email(regexp = "^\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*(\\.\\w{2,3})+$")
  private String email;

  @Column(nullable = false)
  @Size(
          min = 8,
          max = 90,
          message = "Min size of password is 8 characters"
  )
  private String password;

  @Column(nullable = false)
  private Boolean isEnable = false;

  private String profileImage;

  @CreationTimestamp
  private Date dateCreate;

  private String firstName;

  private String lastName;

  private String profileDescription;

  @ElementCollection
  @Enumerated(value = EnumType.STRING)
  private Set<Role> role = new HashSet<>(Set.of(Role.USER));
}