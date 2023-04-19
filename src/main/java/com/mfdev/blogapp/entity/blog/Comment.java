package com.mfdev.blogapp.entity.blog;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.mfdev.blogapp.entity.baseentities.BaseEntity;
import com.mfdev.blogapp.entity.user.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

import static org.hibernate.annotations.OnDeleteAction.CASCADE;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Comment extends BaseEntity {
  @Column(columnDefinition = "text")
  String comment;

  @ManyToOne
  @OnDelete(action = CASCADE)
  private User user;

  @ManyToOne
  @JsonBackReference
  @OnDelete(action = CASCADE)
  private Blog blog;

  @CreationTimestamp
  private Date dateCreate;

  @UpdateTimestamp
  private Date dateUpdate;
}
