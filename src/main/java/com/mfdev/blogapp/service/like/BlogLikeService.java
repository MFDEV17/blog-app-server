package com.mfdev.blogapp.service.like;

import com.mfdev.blogapp.dto.bloglike.BlogLikeDTO;
import com.mfdev.blogapp.repository.like.BlogLikeRepository;
import com.mfdev.blogapp.repository.UserRepository;
import com.mfdev.blogapp.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BlogLikeService {
  private final BlogLikeRepository blogLikeRepository;
  private final UserRepository userRepository;
  private final SecurityUtil securityUtil;

  @PreAuthorize("isFullyAuthenticated()")
  public ResponseEntity<?> setLike(BlogLikeDTO dto) {
    Long userId = userRepository
            .getUserId(securityUtil.getSessionUsername());

    if (isLikeExists(dto)) {
      blogLikeRepository.deleteLike(dto.getBlogId(), userId);
    } else {
      blogLikeRepository.addLike(dto.getBlogId(), userId);
    }
    return ResponseEntity.ok().build();
  }

  private boolean isLikeExists(BlogLikeDTO dto) {
    return blogLikeRepository.isLikeExists(
            dto.getBlogId(),
            securityUtil.getSessionUsername());
  }
}
