package com.mfdev.blogapp.repository.tag;

import com.mfdev.blogapp.entity.tag.Tag;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
  @Modifying
  @Transactional
  @Query(value = "insert into blog_tags(blog_id, tags_id) values (?1, ?2)", nativeQuery = true)
  void addTagToBlog(Long blogId, Long tagId);

  Optional<Tag> findByName(String name);

  @Query("select t.id from Tag t where t.name in (?1)")
  Set<Long> findIdsTags(Set<String> tags);
}
