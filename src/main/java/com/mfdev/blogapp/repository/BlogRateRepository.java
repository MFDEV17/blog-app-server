package com.mfdev.blogapp.repository;

import com.mfdev.blogapp.entity.blog.rate.BlogRate;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BlogRateRepository extends JpaRepository<BlogRate, Long> {

  @Query(nativeQuery = true, value = "insert into blog_rate(vote, blog_id, user_id) VALUES (?1, ?2, ?3)")
  @Modifying
  @Transactional
  void makeVote(Long vote, Long blogId, Long userId);

  @Query(value = "select exists(select username from users left join blog_rate br on users.id = br.user_id where blog_id = ?1 and username = ?2)", nativeQuery = true)
  Boolean checkVote(Long blogId, String username);
}
