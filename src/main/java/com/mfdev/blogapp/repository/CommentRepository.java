package com.mfdev.blogapp.repository;

import com.mfdev.blogapp.entity.blog.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
  Long countAllByBlogId(Long id);
}
