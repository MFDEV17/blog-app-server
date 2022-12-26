package com.mfdev.blogapp.entity.blog;

import com.mfdev.blogapp.entity.user.User;
import com.mfdev.blogapp.service.util.enumconverter.JsonConverter;
import jakarta.persistence.*;
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
  private Blog blog;

  @CreationTimestamp
  private Date dateCreate;

  @UpdateTimestamp
  private Date dateUpdate;
}
