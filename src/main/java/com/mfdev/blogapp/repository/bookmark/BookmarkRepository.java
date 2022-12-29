package com.mfdev.blogapp.repository.bookmark;

import com.mfdev.blogapp.entity.bookmark.Bookmark;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
  boolean existsByUserUsernameAndBlogId(String userUsername, Long blogId);

  @Modifying
  @Transactional
  @Query(value = "insert into bookmark(blog_id, user_id) values (?2, ?1)", nativeQuery = true)
  void saveBookmark(Long userId, Long blogId);

  @Modifying
  @Transactional
  @Query("delete from Bookmark b where b.user.id = ?1 and b.blog.id = ?2")
  void deleteBookmark(Long userId, Long blogId);
}
