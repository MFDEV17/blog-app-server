package com.mfdev.blogapp.entity.like;

import com.mfdev.blogapp.entity.blog.Comment;
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
public class LikeComment {
  @Id
  @GeneratedValue(strategy = IDENTITY)
  private Long id;

  @ManyToOne
  @OnDelete(action = CASCADE)
  private Comment comment;

  @ManyToOne
  @OnDelete(action = CASCADE)
  private User user;

  public LikeComment(Comment comment, User user) {
    this.comment = comment;
    this.user = user;
  }
}
