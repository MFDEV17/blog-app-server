package com.mfdev.blogapp.dto.comment;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class UpdateCommentDTO {
  private Long commentId;
  private Map<String, Object> comment;
}
