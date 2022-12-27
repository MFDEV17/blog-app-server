package com.mfdev.blogapp.dto.blog;

import org.springframework.beans.factory.annotation.Value;

import java.util.Date;
import java.util.Map;

public interface ShortBlogDTO {
  Long getId();

  Map<String, Object> getContent();

  Date getDateCreate();

  UserInfo getUser();

  @Value("#{target.comments.size()}")
  int getCommentCount();

  @Value("#{target.likes.size()}")
  int getLikeCount();

  interface UserInfo {
    Long getId();

    String getUsername();

    String getProfileImage();
  }
}
