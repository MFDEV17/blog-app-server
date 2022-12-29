package com.mfdev.blogapp.entity.bookmark;

import com.mfdev.blogapp.entity.blog.Blog;
import com.mfdev.blogapp.entity.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
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
public class Bookmark {
  @Id
  @GeneratedValue(strategy = IDENTITY)
  private Long id;

  @ManyToOne
  @OnDelete(action = CASCADE)
  private User user;

  @ManyToOne
  @OnDelete(action = CASCADE)
  private Blog blog;

  public Bookmark(User user, Blog blog) {
    this.user = user;
    this.blog = blog;
  }
}
