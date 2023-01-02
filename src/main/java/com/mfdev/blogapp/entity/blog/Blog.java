package com.mfdev.blogapp.entity.blog;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.mfdev.blogapp.entity.blog.rate.BlogRate;
import com.mfdev.blogapp.entity.bookmark.Bookmark;
import com.mfdev.blogapp.entity.like.BlogLike;
import com.mfdev.blogapp.entity.tag.Tag;
import com.mfdev.blogapp.entity.user.User;
import com.mfdev.blogapp.util.converter.JsonConverter;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.Map;
import java.util.Set;

import static jakarta.persistence.CascadeType.*;
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

  @OneToMany(mappedBy = "blog")
  @JsonManagedReference
  private Set<Comment> comments;

  @OneToMany(mappedBy = "blog")
  @JsonManagedReference
  private Set<BlogLike> likes;

  @ManyToMany(cascade = ALL)
  private Set<Tag> tags;

  @OneToMany(mappedBy = "blog")
  @JsonManagedReference
  private Set<Bookmark> bookmarks;

  @OneToMany(mappedBy = "blog")
  @JsonManagedReference
  private Set<BlogRate> rating;

  public Blog(Map<String, Object> content, User user) {
    this.content = content;
    this.user = user;
  }
}
