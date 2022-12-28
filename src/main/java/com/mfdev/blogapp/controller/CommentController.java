package com.mfdev.blogapp.controller;

import com.mfdev.blogapp.dto.comment.CreateCommentDTO.CreateCommentDTO;
import com.mfdev.blogapp.dto.comment.UpdateCommentDTO;
import com.mfdev.blogapp.service.blog.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {
  private final CommentService commentService;

  @PostMapping("/create")
  public ResponseEntity<?> createComment(@RequestBody CreateCommentDTO dto) {
    return commentService.createComment(dto);
  }

  @PutMapping("/update")
  public ResponseEntity<?> updateComment(@RequestBody UpdateCommentDTO dto) {
    return commentService.updateComment(dto);
  }

  @DeleteMapping("/delete/{commentId}")
  public ResponseEntity<?> deleteComment(@PathVariable Long commentId) {
    return commentService.deleteComment(commentId);
  }
}
