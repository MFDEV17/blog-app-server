package com.mfdev.blogapp.entity.baseentities;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

import static jakarta.persistence.GenerationType.IDENTITY;

@MappedSuperclass
public class BaseEntity {
  @Id
  @GeneratedValue(strategy = IDENTITY)
  private Long id;
}
