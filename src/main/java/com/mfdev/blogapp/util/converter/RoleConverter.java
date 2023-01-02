package com.mfdev.blogapp.util.converter;

import com.mfdev.blogapp.entity.user.authority.Role;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class RoleConverter implements Converter<String, Role> {
  @Override
  public Role convert(String source) {
    return Role.valueOf(source);
  }
}
