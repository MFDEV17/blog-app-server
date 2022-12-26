package com.mfdev.blogapp.entity.blog.rate;

public enum RateType {
  PLUS(1L), MINUS(-1L);

  private final Long rate;

  RateType(Long rate) {
    this.rate = rate;
  }

  public Long getRate() {
    return rate;
  }
}
