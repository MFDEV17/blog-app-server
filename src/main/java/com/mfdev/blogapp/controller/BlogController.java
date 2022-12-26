package com.mfdev.blogapp.controller;

import com.mfdev.blogapp.dto.blog.CreateBlogDTO;
import com.mfdev.blogapp.dto.blog.RateBlogDTO;
import com.mfdev.blogapp.dto.blog.UpdateBlogDTO;
import com.mfdev.blogapp.service.BlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}
