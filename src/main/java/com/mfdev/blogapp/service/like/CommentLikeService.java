package com.mfdev.blogapp.service.like;

import com.mfdev.blogapp.dto.comment.CommentLikeDTO;
import com.mfdev.blogapp.repository.user.UserRepository;
import com.mfdev.blogapp.repository.like.CommentLikeRepository;
import com.mfdev.blogapp.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentLikeService {
  private final UserRepository userRepository;
  private final SecurityUtil securityUtil;
  private final CommentLikeRepository commentLikeRepository;

  @PreAuthorize("isFullyAuthenticated()")
  public ResponseEntity<?> likeComment(CommentLikeDTO dto) {
    Long sessionUserId = userRepository.getUserId(securityUtil.getSessionUsername());

    if (!isLikeExists(dto)) {
      commentLikeRepository.likeComment(dto.getCommentId(), sessionUserId);
    } else {
      commentLikeRepository.deleteLike(dto.getCommentId(), sessionUserId);
    }

    return ResponseEntity.ok().build();
  }

  private boolean isLikeExists(CommentLikeDTO dto) {
    return commentLikeRepository
            .isLikeExists(dto.getCommentId(), securityUtil.getSessionUsername());
  }
}
