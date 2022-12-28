package com.mfdev.blogapp.controller;

import com.mfdev.blogapp.service.bookmark.BookmarkService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/bookmark")
public class BookMarkController {
  private final BookmarkService bookmarkService;

  @PostMapping("/save/{blogId}")
  public ResponseEntity<?> saveBookmark(@PathVariable Long blogId) {
    return bookmarkService.saveBlogToBookmark(blogId);
  }

  @DeleteMapping("/delete/{bookmarkId}")
  public ResponseEntity<?> deleteBookmark(@PathVariable Long bookmarkId) {
    return bookmarkService.deleteBookmark(bookmarkId);
  }
}
