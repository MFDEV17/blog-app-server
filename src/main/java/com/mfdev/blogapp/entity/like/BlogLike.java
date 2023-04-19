package com.mfdev.blogapp.entity.like;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.mfdev.blogapp.entity.baseentities.BaseEntity;
import com.mfdev.blogapp.entity.blog.Blog;
import com.mfdev.blogapp.entity.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;

import static org.hibernate.annotations.OnDeleteAction.CASCADE;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(indexes = {
        @Index(columnList = "blog_id, user_id", unique = true)
})
public class BlogLike extends BaseEntity {
  @ManyToOne
  @JsonBackReference
  @OnDelete(action = CASCADE)
  private Blog blog;

  @ManyToOne
  @OnDelete(action = CASCADE)
  private User user;
}
