package com.mfdev.blogapp.entity.user.authority;

import java.util.Set;

import static com.mfdev.blogapp.entity.user.authority.Authority.*;

public enum Role {
  USER(Set.of(CREATE_BLOG, CREATE_COMMENT)),
  MODERATOR(Set.of(
          CREATE_BLOG,
          CREATE_COMMENT,
          EDIT_OTHER_BLOG,
          DELETE_OTHER_BLOG,
          BAN_USER,
          DELETE_OTHER_COMMENT
  ));

  private final Set<Authority> permissions;

  Role(Set<Authority> permissions) {
    this.permissions = permissions;
  }

  public Set<Authority> getPermissions() {
    return permissions;
  }

  public String[] getStringArrayPermissions() {
    return permissions.stream()
            .map(i -> "SCOPE_".concat(i.getAuthority()))
            .toArray(String[]::new);
  }
}
