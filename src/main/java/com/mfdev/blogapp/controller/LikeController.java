package com.mfdev.blogapp.controller;

import com.mfdev.blogapp.dto.CommentLikeDTO;
import com.mfdev.blogapp.dto.blog.bloglike.BlogLikeDTO;
import com.mfdev.blogapp.service.like.BlogLikeService;
import com.mfdev.blogapp.service.like.CommentLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LikeController {
  private final BlogLikeService blogLikeService;
  private final CommentLikeService commentLikeService;

  @PostMapping("/like-post")
  public ResponseEntity<?> likePost(@RequestBody BlogLikeDTO dto) {
    return blogLikeService.setLike(dto);
  }

  @PostMapping("/like-comment")
  public ResponseEntity<?> likeComment(@RequestBody CommentLikeDTO dto) {
    return commentLikeService.likeComment(dto);
  }
}
