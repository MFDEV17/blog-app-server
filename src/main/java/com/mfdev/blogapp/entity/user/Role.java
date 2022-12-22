package com.mfdev.blogapp.entity.user;

import java.util.Set;

import static com.mfdev.blogapp.entity.user.Permission.*;

public enum Role {
  USER(Set.of(
          USER_CREATE_BLOG,
          USER_CREATE_COMMENT)
  ),
  ADMIN(Set.of(
          USER_CREATE_BLOG,
          USER_CREATE_COMMENT,
          ADMIN_SET_ADMIN,
          ADMIN_BAN_USER,
          ADMIN_EDIT_BLOG,
          ADMIN_DELETE_BLOG)
  ),
  SUPER_ADMIN(Set.of(
          USER_CREATE_BLOG,
          USER_CREATE_COMMENT,
          ADMIN_SET_ADMIN,
          ADMIN_BAN_USER,
          ADMIN_EDIT_BLOG,
          ADMIN_DELETE_BLOG,
          SUPER_ADMIN_BAN_ADMIN,
          SUPER_ADMIN_SET_SUPER_ADMIN)
  );

  private final Set<Permission> permissions;

  Role(Set<Permission> permissions) {
    this.permissions = permissions;
  }

  public Set<Permission> getPermissions() {
    return permissions;
  }

  public String[] getStringArrayPermissions() {
    return permissions.stream()
            .map(i -> "SCOPE_".concat(i.name()))
            .toArray(String[]::new);
  }
}
