package com.mfdev.blogapp.security.details;

import com.mfdev.blogapp.entity.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@RequiredArgsConstructor
public class UserDetailsImpl implements UserDetails {
  private final User user;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return user.getRole().getPermissions();
  }

  @Override
  public String getPassword() {
    return user.getPassword();
  }

  @Override
  public String getUsername() {
    return user.getUsername();
  }

  @Override
  public boolean isAccountNonExpired() {
    return user.getIsEnable();
  }

  @Override
  public boolean isAccountNonLocked() {
    return user.getIsEnable();
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return user.getIsEnable();
  }

  @Override
  public boolean isEnabled() {
    return user.getIsEnable();
  }
}
