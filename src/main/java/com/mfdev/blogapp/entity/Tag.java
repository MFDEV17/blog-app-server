package com.mfdev.blogapp.entity;

import com.mfdev.blogapp.entity.blog.Blog;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;

import java.util.Set;

import static jakarta.persistence.GenerationType.IDENTITY;
import static org.hibernate.annotations.OnDeleteAction.NO_ACTION;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Tag {
  @Id
  @GeneratedValue(strategy = IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true)
  private String name;

  @ManyToMany
  @OnDelete(action = NO_ACTION)
  Set<Blog> blogs;
}
