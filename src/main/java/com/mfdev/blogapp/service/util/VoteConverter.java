package com.mfdev.blogapp.service.util;

import com.mfdev.blogapp.entity.blog.rate.RateType;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class VoteConverter implements Converter<String, RateType> {
  @Override
  public RateType convert(String source) {
    return RateType.valueOf(source);
  }
}
