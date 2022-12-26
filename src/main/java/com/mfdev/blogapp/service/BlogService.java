package com.mfdev.blogapp.service;

import com.mfdev.blogapp.dto.blog.CreateBlogDTO;
import com.mfdev.blogapp.dto.blog.RateBlogDTO;
import com.mfdev.blogapp.dto.blog.UpdateBlogDTO;
import com.mfdev.blogapp.entity.blog.Blog;
import com.mfdev.blogapp.entity.user.User;
import com.mfdev.blogapp.repository.BlogRateRepository;
import com.mfdev.blogapp.repository.BlogRepository;
import com.mfdev.blogapp.repository.UserRepository;
import com.mfdev.blogapp.service.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class BlogService {
  private final SecurityUtil securityUtil;
  private final BlogRepository blogRepository;
  private final UserRepository userRepository;
  private final BlogRateRepository blogRateRepository;

  @PreAuthorize("isFullyAuthenticated()")
  public ResponseEntity<?> createBlog(CreateBlogDTO dto) {
    User user = userRepository.findUserByUsername(securityUtil.getSessionUsername())
            .orElseThrow(() -> new UsernameNotFoundException("Not found"));

    blogRepository.save(new Blog(dto.getContent(), user));

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
    blogRepository.updateBlog(dto.getContent(), dto.getBlogID());

    return ResponseEntity.ok("Post has been edited");
  }

  @PreAuthorize(
          "isFullyAuthenticated() " +
                  "and authentication.name" +
                  ".equals(@blogRepository.getBlogAuthorUsername(#blogID)) " +
                  "or hasAuthority('SCOPE_'" +
                  ".concat(T(com.mfdev.blogapp.entity.user.authority.Authority)" +
                  ".DELETE_OTHER_BLOG.name()))")
  public ResponseEntity<?> deleteBlog(Long blogID) {
    blogRepository.deletePost(blogID);
    return ResponseEntity.ok("Post has been deleted");
  }

  @PreAuthorize("isFullyAuthenticated()")
  public ResponseEntity<?> setBlogRate(RateBlogDTO dto) {
    if (!blogRateRepository.checkVote(dto.getBlogID(), securityUtil.getSessionUsername())) {
      Long userId = userRepository.getUserId(securityUtil.getSessionUsername());

      blogRateRepository.makeVote(dto.getRateType().getRate(), dto.getBlogID(), userId);

      return ResponseEntity.ok("Thanks for vote");
    }

    return ResponseEntity.badRequest().body("You already voted earlier");
  }
}