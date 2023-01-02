package com.mfdev.blogapp.controller;

import com.mfdev.blogapp.dto.blog.*;
import com.mfdev.blogapp.service.blog.BlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/blog")
public class BlogController {
  private final BlogService blogService;

  @PostMapping("/create")
  public ResponseEntity<?> createBlog(@RequestBody CreateBlogDTO dto) {
    return blogService.createBlog(dto);
  }

  @PutMapping("/update")
  public ResponseEntity<?> updateBlog(@RequestBody UpdateBlogDTO dto) {
    return blogService.updateBlog(dto);
  }

  @DeleteMapping("/delete/{blogId}")
  public ResponseEntity<?> deleteBlog(@PathVariable Long blogId) {
    return blogService.deleteBlog(blogId);
  }

  @PostMapping("/vote")
  public ResponseEntity<?> voteForBlog(@RequestBody RateBlogDTO dto) {
    return blogService.setBlogRate(dto);
  }

  @GetMapping("/{username}/{page}")
  public Collection<ShortBlogDTO> getAllUserPosts(@PathVariable String username, @PathVariable Integer page) {
    return blogService.getUserBlogs(username, page);
  }

  @GetMapping("/home/{path}")
  public List<ShortBlogDTO> getHomepageBlogs(@PathVariable Integer path) {
    return blogService.getHomePageBlogs(path);
  }

  @GetMapping("/{blogId}")
  public FullBlogDTO getBlog(@PathVariable Long blogId) {
    return blogService.getBlog(blogId);
  }
}
