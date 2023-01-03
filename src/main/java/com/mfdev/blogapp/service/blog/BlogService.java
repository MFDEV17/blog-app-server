package com.mfdev.blogapp.service.blog;

import com.mfdev.blogapp.dto.blog.*;
import com.mfdev.blogapp.entity.blog.Blog;
import com.mfdev.blogapp.entity.tag.Tag;
import com.mfdev.blogapp.entity.user.User;
import com.mfdev.blogapp.repository.blog.BlogRateRepository;
import com.mfdev.blogapp.repository.blog.BlogRepository;
import com.mfdev.blogapp.repository.tag.TagRepository;
import com.mfdev.blogapp.repository.user.UserRepository;
import com.mfdev.blogapp.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class BlogService {
  private final SecurityUtil securityUtil;
  private final BlogRepository blogRepository;
  private final UserRepository userRepository;
  private final BlogRateRepository blogRateRepository;
  private final TagRepository tagRepository;

  @PreAuthorize("isFullyAuthenticated()")
  public ResponseEntity<?> createBlog(CreateBlogDTO dto) {
    User user = User.builder()
            .id(securityUtil.getSessionUserId()).build();

    Long postId = blogRepository
            .save(new Blog(dto.getContent(), user))
            .getId();

    Set<Long> ids = new HashSet<>();

    dto.getTags()
            .forEach(tag -> {
              try {
                ids.add(tagRepository
                        .save(new Tag(tag.getName())).getId());
              } catch (DataIntegrityViolationException e) {
                String message = Objects.requireNonNull(e.getRootCause()).getMessage();
                String existingTag = StringUtils.substringBetween(message, "=(", ")");

                log.info("Existing key found: '{}'. Fetching id of tag...", existingTag);

                Long tagId = tagRepository
                        .findByName(existingTag).get().getId();

                log.info("Id of '{}' is {}. Inserting in ids list...", existingTag, tagId);

                ids.add(tagId);
              }
            });

    ids.forEach(id -> tagRepository.addTagToBlog(postId, id));

    return ResponseEntity.ok("Post has been created");
  }

  @PreAuthorize(
          "isFullyAuthenticated() " +
                  "and authentication.name" +
                  ".equals(@blogRepository.getBlogAuthorUsername(#dto.blogID)) " +
                  "or hasAuthority('SCOPE_'" +
                  ".concat(T(com.mfdev.blogapp.entity.user.authority.Authority)" +
                  ".EDIT_OTHER_BLOG.name()))")
  public ResponseEntity<?> updateBlog(UpdateBlogDTO dto) {
    if (blogRepository.existsById(dto.getBlogID())) {
      blogRepository.updateBlog(dto.getContent(), dto.getBlogID());

      return ResponseEntity.ok("Post has been edited");
    }

    return ResponseEntity
            .badRequest()
            .body("The post does not exist or has been deleted");

  }

  @PreAuthorize(
          "isFullyAuthenticated() " +
                  "and authentication.name" +
                  ".equals(@blogRepository.getBlogAuthorUsername(#blogID)) " +
                  "or hasAuthority('SCOPE_'" +
                  ".concat(T(com.mfdev.blogapp.entity.user.authority.Authority)" +
                  ".DELETE_OTHER_BLOG.name()))")
  public ResponseEntity<?> deleteBlog(Long blogID) {
    if (blogRepository.existsById(blogID)) {
      blogRepository.deletePost(blogID);
      return ResponseEntity.ok("Post has been deleted");
    }

    return ResponseEntity.badRequest()
            .body("The post does not exist or has been deleted");
  }

  @PreAuthorize("isFullyAuthenticated()")
  public ResponseEntity<?> setBlogRate(RateBlogDTO dto) {
    if (!blogRateRepository.checkVote(
            dto.getBlogID(),
            securityUtil.getSessionUsername())) {

      Long userId = userRepository
              .getUserId(securityUtil.getSessionUsername());

      blogRateRepository.makeVote(
              dto.getRateType().getRate(),
              dto.getBlogID(),
              userId);

      return ResponseEntity.ok("Thanks for vote");
    }

    return ResponseEntity
            .badRequest()
            .body("You already voted earlier");
  }

  @PreAuthorize("permitAll()")
  public List<ShortBlogDTO> getUserBlogs(String username, Integer path) {
    return blogRepository.findAllByUserUsername(
            username,
            PageRequest.of(
                    path, 10,
                    Sort.by("dateCreate").descending()
            )
    );
  }

  @PreAuthorize("permitAll()")
  public List<ShortBlogDTO> getHomePageBlogs(Integer path) {
    return blogRepository.findBy(PageRequest.of(path, 10));
  }

  @PreAuthorize("permitAll()")
  public FullBlogDTO getBlog(Long blogId) {
    return blogRepository.findBlogById(blogId);
  }

  @PreAuthorize("permitAll()")
  public List<ShortBlogDTO> findBlogsByTags(Set<String> tags, Integer path) {
    Set<Long> idsTags = tagRepository.findIdsTags(tags);
    return blogRepository.findAllByTagsIdIn(idsTags, PageRequest.of(path, 10));
  }
}
