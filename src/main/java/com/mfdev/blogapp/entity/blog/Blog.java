package com.mfdev.blogapp.entity.blog;

import com.fasterxml.jackson.annotation.JsonRawValue;
import com.mfdev.blogapp.entity.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Blog {
  @Id
  @GeneratedValue(strategy = IDENTITY)
  private Long id;

  @JsonRawValue
  @Column(columnDefinition = "json")
  private String content;

  @CreationTimestamp
  private Date dateCreate;

  @UpdateTimestamp
  private Date dateUpdate;

  @ManyToOne
  private User user;
}
