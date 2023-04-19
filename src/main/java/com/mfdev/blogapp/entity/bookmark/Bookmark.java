package com.mfdev.blogapp.entity.bookmark;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.mfdev.blogapp.entity.baseentities.BaseEntity;
import com.mfdev.blogapp.entity.blog.Blog;
import com.mfdev.blogapp.entity.user.User;
import jakarta.persistence.*;
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
@Table(indexes = {
        @Index(columnList = "blog_id, user_id", unique = true)
})
public class Bookmark extends BaseEntity {
  @ManyToOne
  @OnDelete(action = CASCADE)
  private User user;

  @ManyToOne
  @OnDelete(action = CASCADE)
  @JsonBackReference
  private Blog blog;
}
