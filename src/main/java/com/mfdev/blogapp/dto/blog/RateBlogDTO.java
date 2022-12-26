package com.mfdev.blogapp.dto.blog;

import com.mfdev.blogapp.entity.blog.rate.RateType;
import com.mfdev.blogapp.entity.user.User;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class RateBlogDTO {
  private Long blogID;
  private RateType rateType;
}
