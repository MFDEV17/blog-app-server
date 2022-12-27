package com.mfdev.blogapp.repository.like;

import com.mfdev.blogapp.entity.like.LikeComment;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentLikeRepository extends JpaRepository<LikeComment, Long> {

  @Query(value = "select exists(select username from like_comment left join users u on u.id = like_comment.user_id where comment_id = ?1 and username = ?2)", nativeQuery = true)
  Boolean isLikeExists(Long commentId, String username);

  @Modifying
  @Transactional
  @Query(value = "insert into like_comment(comment_id, user_id) values (?1, ?2)", nativeQuery = true)
  void likeComment(Long commentId, Long userId);

  @Modifying
  @Transactional
  @Query(value = "delete from like_comment where comment_id = ?1 and user_id = ?2", nativeQuery = true)
  void deleteLike(Long commentId, Long userId);
}
