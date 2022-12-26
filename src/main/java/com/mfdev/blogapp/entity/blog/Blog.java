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
public class Blog {
  @Id
  @GeneratedValue(strategy = IDENTITY)
  private Long id;

  @Convert(converter = JsonConverter.class)
  @Column(columnDefinition = "text")
  Map<String, Object> content;

  @CreationTimestamp
  private Date dateCreate;

  @UpdateTimestamp
  private Date dateUpdate;

  @ManyToOne
  private User user;

  public Blog(Map<String, Object> content, User user) {
    this.content = content;
    this.user = user;
  }
}
