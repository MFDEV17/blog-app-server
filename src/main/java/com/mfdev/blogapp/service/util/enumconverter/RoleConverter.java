package com.mfdev.blogapp.service.util.enumconverter;

import com.mfdev.blogapp.entity.user.authority.Role;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class RoleConverter implements Converter<String, Role> {
  @Override
  public Role convert(String source) {
    return Role.valueOf(source);
  }
}
