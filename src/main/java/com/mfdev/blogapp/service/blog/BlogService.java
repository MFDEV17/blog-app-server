package com.mfdev.blogapp.service.blog;

import com.mfdev.blogapp.dto.blog.CreateBlogDTO;
import com.mfdev.blogapp.dto.blog.RateBlogDTO;
import com.mfdev.blogapp.dto.blog.ShortBlogDTO;
import com.mfdev.blogapp.dto.blog.UpdateBlogDTO;
import com.mfdev.blogapp.repository.user.UserRepository;
import com.mfdev.blogapp.repository.blog.BlogRateRepository;
import com.mfdev.blogapp.repository.blog.BlogRepository;
import com.mfdev.blogapp.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

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
    blogRepository.saveBlogByUserId(
            userRepository.getUserId(securityUtil.getSessionUsername()),
            dto.getContent()
    );

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
}
