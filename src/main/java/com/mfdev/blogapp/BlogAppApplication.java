package com.mfdev.blogapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class BlogAppApplication {

  public static void main(String[] args) {
    SpringApplication.run(BlogAppApplication.class, args);
  }

}
