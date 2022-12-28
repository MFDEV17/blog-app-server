package com.mfdev.blogapp.dto.comment;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentLikeDTO {
  private Long userId;
  private Long commentId;
}
