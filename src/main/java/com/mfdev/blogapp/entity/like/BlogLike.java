package com.mfdev.blogapp.entity.like;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.mfdev.blogapp.entity.blog.Blog;
import com.mfdev.blogapp.entity.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;

import static jakarta.persistence.GenerationType.IDENTITY;
import static org.hibernate.annotations.OnDeleteAction.CASCADE;

@Entity
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = {"id"})
public class BlogLike {
  @Id
  @GeneratedValue(strategy = IDENTITY)
  private Long id;

  @ManyToOne
  @JsonBackReference
  @OnDelete(action = CASCADE)
  private Blog blog;

  @ManyToOne
  @OnDelete(action = CASCADE)
  private User user;

  public BlogLike(Blog blog, User user) {
    this.blog = blog;
    this.user = user;
  }
}
