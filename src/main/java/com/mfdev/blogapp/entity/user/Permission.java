package com.mfdev.blogapp.entity.user;

import org.springframework.security.core.GrantedAuthority;

public enum Permission implements GrantedAuthority {
  USER_CREATE_BLOG("user:create-post"),
  USER_CREATE_COMMENT("user:create-comment"),
  ADMIN_BAN_USER("admin:ban-user"),
  ADMIN_EDIT_BLOG("admin:edit-blog"),
  ADMIN_DELETE_BLOG("admin:delete-blog"),
  ADMIN_SET_ADMIN("admin:set-admin"),
  SUPER_ADMIN_BAN_ADMIN("super_admin:ban-admin"),
  SUPER_ADMIN_SET_SUPER_ADMIN("super_admin:set-super-admin");

  private final String permission;

  Permission(String permission) {
    this.permission = permission;
  }

  @Override
  public String getAuthority() {
    return permission;
  }
}
