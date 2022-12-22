package com.mfdev.blogapp.entity.blog;

import com.mfdev.blogapp.entity.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class BlogRate {
  @Id
  @GeneratedValue(strategy = IDENTITY)
  private Long id;

  @Column(nullable = false)
  private Long vote;

  @ManyToOne
  private Blog blog;

  @ManyToOne
  private User user;
}
