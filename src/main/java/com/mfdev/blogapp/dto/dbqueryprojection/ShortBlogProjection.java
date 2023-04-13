package com.mfdev.blogapp.dto.dbqueryprojection;

import org.springframework.beans.factory.annotation.Value;

import java.util.Date;
import java.util.Set;

public interface ShortBlogProjection {
  Long getId();

  String getContent();

  Date getDateCreate();

  Date getDateUpdate();

  UserInfo getUser();

  @Value("#{target.comments.size()}")
  int getCommentCount();

  @Value("#{target.likes.size()}")
  int getLikeCount();

  @Value("#{T(com.mfdev.blogapp.util.RateUtil).rateSummary(target.rating)}")
  int getRatingVal();

  Set<ShortTags> getTags();

  interface ShortTags {
    String getName();
  }

  interface UserInfo {
    Long getId();

    String getUsername();

    String getProfileImage();
  }
}
