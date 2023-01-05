package com.mfdev.blogapp.dto.comment;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class CreateCommentDTO {
  private Long userId;
  private Long blogId;
  private Map<String, Object> comment;
}
