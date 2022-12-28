package com.mfdev.blogapp.entity.user.authority;

import org.springframework.security.core.GrantedAuthority;

public enum Authority implements GrantedAuthority {
  CREATE_BLOG,
  CREATE_COMMENT,
  EDIT_OTHER_COMMENT,
  DELETE_OTHER_COMMENT,
  DELETE_OTHER_BLOG,
  DELETE_OTHER_ACCOUNT,
  EDIT_OTHER_BLOG,
  SET_USER_AUTHORITY,
  BAN_USER;


  @Override
  public String getAuthority() {
    return name();
  }
}
