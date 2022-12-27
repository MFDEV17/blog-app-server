package com.mfdev.blogapp.entity.blog;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.mfdev.blogapp.entity.user.User;
import com.mfdev.blogapp.service.util.enumconverter.JsonConverter;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.Map;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class Comment {
  @Id
  @GeneratedValue(strategy = IDENTITY)
  private Long id;

  @Convert(converter = JsonConverter.class)
  @Column(columnDefinition = "text")
  Map<String, Object> comment;

  @ManyToOne
  private User user;

  @ManyToOne
  @JsonBackReference
  private Blog blog;

  @CreationTimestamp
  private Date dateCreate;

  @UpdateTimestamp
  private Date dateUpdate;

  public Comment(Map<String, Object> comment, User user, Blog blog) {
    this.comment = comment;
    this.user = user;
    this.blog = blog;
  }
}
