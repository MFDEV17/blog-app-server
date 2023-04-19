package com.mfdev.blogapp.entity.blog;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.mfdev.blogapp.entity.baseentities.BaseEntity;
import com.mfdev.blogapp.entity.blog.rate.Rating;
import com.mfdev.blogapp.entity.bookmark.Bookmark;
import com.mfdev.blogapp.entity.like.BlogLike;
import com.mfdev.blogapp.entity.tag.Tag;
import com.mfdev.blogapp.entity.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.Set;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class Blog extends BaseEntity {
  @Column(columnDefinition = "text")
  private String content;

  @CreationTimestamp
  private Date dateCreate;

  @UpdateTimestamp
  private Date dateUpdate;

  @ManyToOne(fetch = LAZY)
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
  private Set<Rating> rating;
}
