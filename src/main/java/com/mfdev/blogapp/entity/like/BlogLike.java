package com.mfdev.blogapp.entity.like;

import com.mfdev.blogapp.entity.blog.Blog;
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
public class BlogLike {
  @Id
  @GeneratedValue(strategy = IDENTITY)
  private Long id;

  @ManyToOne
  private Blog blog;

  @ManyToOne
  private User user;
}
