package com.mfdev.blogapp.service.blog;

import com.mfdev.blogapp.dto.dbqueryprojection.FullBlogProjection;
import com.mfdev.blogapp.dto.dbqueryprojection.ShortBlogProjection;
import com.mfdev.blogapp.dto.requestdto.blog.*;
import com.mfdev.blogapp.entity.blog.Blog;
import com.mfdev.blogapp.entity.blog.rate.RateType;
import com.mfdev.blogapp.entity.tag.Tag;
import com.mfdev.blogapp.mapper.blog.BlogMapper;
import com.mfdev.blogapp.mapper.rate.RateMapper;
import com.mfdev.blogapp.repository.blog.BlogRepository;
import com.mfdev.blogapp.repository.rating.RatingRepository;
import com.mfdev.blogapp.repository.tag.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

@Service
@RequiredArgsConstructor
public class BlogService {

  private final BlogRepository blogRepository;
  private final TagRepository tagRepository;
  private final BlogMapper blogMapper;
  private final RatingRepository ratingRepository;
  private final RateMapper rateMapper;

  public ResponseEntity<?> createBlog(CreateBlogDTO dto) {
    Blog blog = blogMapper.createBlogDtoToBlog(dto);

    Set<Tag> tags = tagRepository.findAllByNameIn(dto.getTags());

    if (tags.isEmpty()) {
      blog.setTags(dto.getTags().stream()
              .map(Tag::new)
              .collect(toSet()));
    }

    if (tags.size() == dto.getTags().size()) {
      blog.setTags(tags);
    }

    if (tags.size() != dto.getTags().size()) {
      tags.addAll(
              dto.getTags()
                      .stream()
                      .filter(t -> !(tags.stream()
                              .map(Tag::getName)
                              .toList().contains(t))
                      )
                      .map(Tag::new)
                      .collect(toSet())
      );

      blog.setTags(tags);
    }

    blogRepository.save(blog);

    return ResponseEntity.ok("Post has been created");
  }

  public ResponseEntity<?> updateBlog(UpdateBlogDTO dto) {
    if (blogRepository.existsById(dto.getBlogID())) {
      blogRepository.updateBlog(dto.getContent(), dto.getBlogID());
      return ResponseEntity.ok("Post has been edited");
    } else {
      return ResponseEntity.badRequest().body("The post has been deleted or not exists");
    }
  }

  public ResponseEntity<?> deleteBlog(Long blogID) {
    if (blogRepository.existsById(blogID)) {
      blogRepository.deletePost(blogID);
      return ResponseEntity.ok("Post has been deleted");
    }

    return ResponseEntity.badRequest()
            .body("The post does not exist or has been deleted");
  }

  public ResponseEntity<?> voteForBlog(RateBlogDTO dto) {
    try {
      ratingRepository.save(rateMapper.rateBlogDtoToBlogRate(dto));
    } catch (DataIntegrityViolationException e) {
      RateType rateType = ratingRepository.findRatingTypeByIdAndUserId(dto.getBlogID(), 1L);

      if (rateType.equals(dto.getRateType())) {
        ratingRepository.deleteRateByUserAndBlog(1L, dto.getBlogID());
      } else {
        ratingRepository.updateOnUserIdAndBlogId(dto.getRateType(), 1L, dto.getBlogID());
      }

    }

    return ResponseEntity.status(HttpStatus.OK).build();
  }

  public List<ShortBlogProjection> getUserBlogs(String username, Integer path) {
    return blogRepository.justFind(
            username,
            PageRequest.of(
                    path, 10,
                    Sort.by("dateCreate").descending()
            )
    );
  }

  public FullBlogProjection getFullViewBlog(Long blogId) {
    return blogRepository.findAllById(blogId);
  }

  public ShortBlogProjection getShortViewBlog(Long blogId) {
    return blogRepository.findBlogById(blogId);
  }

  public List<ShortBlogProjection> findBlogsByTags(Set<String> tags, Integer path) {
    Set<Long> idsTags = tagRepository.findIdsTags(tags);
    return blogRepository.findAllByTagsIdIn(idsTags, PageRequest.of(path, 10));
  }
}
