package com.mfdev.blogapp.repository.comment;

import com.mfdev.blogapp.entity.blog.Comment;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

  @Modifying
  @Transactional
  @Query(value = "insert into comment(comment, user_id, blog_id) values (?1, ?2, ?3)", nativeQuery = true)
  void saveComment(Map<String, Object> comment, Long userId, Long blogId);


  @Modifying
  @Transactional
  @Query("update Comment c set c.comment = ?1 where c.id = ?2")
  void updateComment(Map<String, Object> comment, Long commentId);

  @Query("select u.username from users u left join Comment c on u.id = c.user.id where c.id = ?1")
  Long getCommentCreatorUsername(Long commentId);

  @Modifying
  @Transactional
  @Query("delete from Comment c where c.id = ?1")
  void deleteCommentById(Long id);
}
