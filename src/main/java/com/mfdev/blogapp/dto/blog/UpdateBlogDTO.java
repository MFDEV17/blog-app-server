package com.mfdev.blogapp.dto.blog;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class UpdateBlogDTO {
  private Long blogID;
  private Map<String, Object> content;
}
