package com.mfdev.blogapp.dto.blog;

import com.mfdev.blogapp.entity.tag.Tag;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;
import java.util.Set;

@Getter
@Setter
public class CreateBlogDTO {
  private Map<String, Object> content;
  private Set<Tag> tags;
}
