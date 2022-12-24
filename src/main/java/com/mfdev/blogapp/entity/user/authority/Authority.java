package com.mfdev.blogapp.entity.user.authority;

import org.springframework.security.core.GrantedAuthority;

public enum Authority implements GrantedAuthority {
  CREATE_BLOG,
  CREATE_COMMENT,
  DELETE_OTHER_COMMENT,
  DELETE_OTHER_BLOG,
  DELETE_OTHER_ACCOUNT,
  EDIT_OTHER_BLOG,
  BAN_USER;


  @Override
  public String getAuthority() {
    return name();
  }
}
