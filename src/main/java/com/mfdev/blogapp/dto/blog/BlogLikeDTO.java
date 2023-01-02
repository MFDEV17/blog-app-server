package com.mfdev.blogapp.dto.blog;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BlogLikeDTO {
  private Long blogId;
  private Long userId;
}
