package com.mfdev.blogapp.entity;

import com.mfdev.blogapp.entity.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.UUID;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VerificationCode {
  @Id
  @GeneratedValue(strategy = IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true)
  private UUID code;

  @OneToOne
  @OnDelete(action = OnDeleteAction.CASCADE)
  private User user;

  public VerificationCode(UUID code, User user) {
    this.code = code;
    this.user = user;
  }
}
