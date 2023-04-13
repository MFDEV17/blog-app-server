package com.mfdev.blogapp.entity.blog.rate;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
@Entity
@Table(indexes = {
        @Index(columnList = "blog_id, user_id", unique = true)
})
public class Rating {
  @Id
  @GeneratedValue(strategy = IDENTITY)
  private Long id;

  @Column(nullable = false)
  private Long vote;

  @ManyToOne
  @JsonBackReference
  private Blog blog;

  @ManyToOne
  private User user;
}
