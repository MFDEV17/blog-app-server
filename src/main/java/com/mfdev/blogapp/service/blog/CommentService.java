package com.mfdev.blogapp.service.blog;

import com.mfdev.blogapp.dto.comment.CreateCommentDTO.CreateCommentDTO;
import com.mfdev.blogapp.dto.comment.UpdateCommentDTO;
import com.mfdev.blogapp.repository.comment.CommentRepository;
import com.mfdev.blogapp.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {
  private final CommentRepository commentRepository;
  private final SecurityUtil securityUtil;

  @PreAuthorize("isFullyAuthenticated()")
  public ResponseEntity<?> createComment(CreateCommentDTO dto) {
    dto.setUserId(securityUtil.getSessionUserId());
    commentRepository.saveComment(
            dto.getComment(),
            dto.getUserId(),
            dto.getBlogId()
    );

    return ResponseEntity.ok("Comment has been created");
  }

  @PreAuthorize(
          "isFullyAuthenticated() and " +
                  "authentication.name" +
                  ".equals(@commentRepository.getCommentCreatorUsername(#dto.commentId)) " +
                  "or hasAuthority('SCOPE_'" +
                  ".concat(T(com.mfdev.blogapp.entity.user.authority.Authority)" +
                  ".EDIT_OTHER_COMMENT))")
  public ResponseEntity<?> updateComment(UpdateCommentDTO dto) {
    commentRepository.updateComment(dto.getComment(), dto.getCommentId());
    return ResponseEntity.ok("Comment has been edited");
  }

  @PreAuthorize(
          "isFullyAuthenticated() and " +
                  "authentication.name" +
                  ".equals(@commentRepository.getCommentCreatorUsername(#commentId)) " +
                  "or hasAuthority('SCOPE_'" +
                  ".concat(T(com.mfdev.blogapp.entity.user.authority.Authority)" +
                  ".DELETE_OTHER_COMMENT))")
  public ResponseEntity<?> deleteComment(Long commentId) {
    commentRepository.deleteCommentById(commentId);
    return ResponseEntity.ok("Comment has been deleted");
  }
}
