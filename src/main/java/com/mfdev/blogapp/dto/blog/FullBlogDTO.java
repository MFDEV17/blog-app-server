package com.mfdev.blogapp.dto.blog;

import org.springframework.beans.factory.annotation.Value;

import java.util.Date;
import java.util.Map;
import java.util.Set;

public interface FullBlogDTO {
  Long getId();

  Set<ShortComment> getComments();

  Map<String, Object> getContent();

  @Value("#{target.likes.size()}")
  Long getLikeCount();

  @Value("#{target.bookmarks.size()}")
  Long getBookmarkCount();

  interface ShortComment {
    Long getId();

    Map<String, Object> getComment();

    UserInfo getUser();

    Date getDateCreate();
    Date getDateUpdate();
  }

  interface UserInfo {
    Long getId();

    String getUsername();

    String getProfileImage();
  }
}
