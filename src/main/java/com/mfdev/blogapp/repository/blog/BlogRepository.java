package com.mfdev.blogapp.repository.blog;

import com.mfdev.blogapp.dto.blog.ShortBlogDTO;
import com.mfdev.blogapp.entity.blog.Blog;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
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
  @Query(nativeQuery = true, value = "insert into blog(content, user_id) values (?1, ?2)")
  void saveBlogByUserId(Long id, Map<String, Object> content);

  @Modifying
  @Transactional
  @Query("delete from Blog where id = ?1")
  void deletePost(Long postId);

  boolean existsById(Long id);

  @EntityGraph(attributePaths = {"likes", "comments", "user"})
  List<ShortBlogDTO> findAllByUserUsername(String username, Pageable pageable);

  @EntityGraph(attributePaths = {"likes", "comments", "user"})
  List<ShortBlogDTO> findBy(Pageable pageable);
}
