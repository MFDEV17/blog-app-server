package com.mfdev.blogapp.repository.like;

import com.mfdev.blogapp.entity.like.BlogLike;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BlogLikeRepository extends JpaRepository<BlogLike, Long> {

  @Query(value = "select exists(select username from blog_like left join users u on u.id = blog_like.user_id where blog_id = ?1 and username = ?2)", nativeQuery = true)
  Boolean isLikeExists(Long blogId, String username);

  @Modifying
  @Transactional
  @Query(value = "delete from blog_like where blog_id = ?1 and user_id = ?2", nativeQuery = true)
  void deleteLike(Long blogId, Long userId);

  @Modifying
  @Transactional
  @Query(value = "insert into blog_like(blog_id, user_id) values (?1, ?2)", nativeQuery = true)
  void addLike(Long blogId, Long userId);
}
