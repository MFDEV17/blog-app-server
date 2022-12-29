package com.mfdev.blogapp.entity.tag;

import com.mfdev.blogapp.entity.blog.Blog;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;

import java.util.Set;

import static jakarta.persistence.GenerationType.IDENTITY;
import static org.hibernate.annotations.OnDeleteAction.NO_ACTION;

@Entity
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = "name")
public class Tag {
  @Id
  @GeneratedValue(strategy = IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true)
  private String name;

  public Tag(String name) {
    this.name = name;
  }
}
