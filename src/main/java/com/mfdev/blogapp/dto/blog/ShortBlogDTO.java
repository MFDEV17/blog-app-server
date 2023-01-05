package com.mfdev.blogapp.dto.blog;

import com.mfdev.blogapp.entity.tag.Tag;
import org.springframework.beans.factory.annotation.Value;

import java.util.Date;
import java.util.Map;
import java.util.Set;

public interface ShortBlogDTO {
  Long getId();

  Map<String, Object> getContent();

  Date getDateCreate();

  UserInfo getUser();

  @Value("#{target.comments.size()}")
  int getCommentCount();

  @Value("#{target.likes.size()}")
  int getLikeCount();

  Set<Tag> getTags();

  interface UserInfo {
    Long getId();

    String getUsername();

    String getProfileImage();
  }
}
