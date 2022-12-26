package com.mfdev.blogapp.repository;

import com.mfdev.blogapp.entity.blog.Blog;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public interface BlogRepository extends JpaRepository<Blog, Long> {

  @Modifying
  @Transactional
  @Query("update Blog b set b.content = ?1 where b.id = ?2")
  void updateBlog(Map<String, Object> content, Long id);

  @Query("select u.username from users u left outer join Blog b on b.id = u.id where b.id = ?1")
  String getBlogAuthorUsername(Long id);

  @Modifying
  @Transactional
  @Query("delete from Blog where id = ?1")
  void deletePost(Long postId);
}
