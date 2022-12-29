package com.mfdev.blogapp.service.bookmark;

import com.mfdev.blogapp.repository.bookmark.BookmarkRepository;
import com.mfdev.blogapp.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookmarkService {
  private final SecurityUtil securityUtil;
  private final BookmarkRepository bookmarkRepository;

  @PreAuthorize("isFullyAuthenticated()")
  public ResponseEntity<?> saveBlogToBookmark(Long blogId) {
    if (!isBookmarkExists(blogId)) {
      bookmarkRepository.saveBookmark(securityUtil.getSessionUserId(), blogId);
      return ResponseEntity.ok("Blog has been added to your bookmarks");
    } else {
      bookmarkRepository.deleteBookmark(securityUtil.getSessionUserId(), blogId);
      return ResponseEntity.ok("Blog has been deleted from your bookmarks");
    }
  }

  @PreAuthorize("isFullyAuthenticated()")
  public ResponseEntity<?> deleteBookmark(Long bookmarkId) {
    bookmarkRepository.deleteById(bookmarkId);
    return ResponseEntity.ok("Blog has been deleted from your bookmarks");
  }

  private boolean isBookmarkExists(Long blogId) {
    return bookmarkRepository
            .existsByUserUsernameAndBlogId(securityUtil.getSessionUsername(), blogId);
  }
}
