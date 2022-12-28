package com.mfdev.blogapp;

import com.mfdev.blogapp.entity.blog.Blog;
import com.mfdev.blogapp.entity.blog.Comment;
import com.mfdev.blogapp.entity.like.BlogLike;
import com.mfdev.blogapp.entity.like.LikeComment;
import com.mfdev.blogapp.entity.user.User;
import com.mfdev.blogapp.entity.user.authority.Role;
import com.mfdev.blogapp.repository.CommentRepository;
import com.mfdev.blogapp.repository.UserRepository;
import com.mfdev.blogapp.repository.blog.BlogRepository;
import com.mfdev.blogapp.repository.like.BlogLikeRepository;
import com.mfdev.blogapp.repository.like.CommentLikeRepository;
import com.mfdev.blogapp.util.enumconverter.JsonConverter;
import net.datafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@SpringBootApplication
public class BlogAppApplication {

  public static void main(String[] args) {
    SpringApplication.run(BlogAppApplication.class, args);
  }

  @Bean
  CommandLineRunner runner(
          UserRepository userRepository,
          BlogRepository blogRepository,
          BlogLikeRepository blogLikeRepository,
          CommentRepository commentRepository,
          CommentLikeRepository commentLikeRepository
  ) {
    Faker faker = new Faker();
    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    return args -> {
      try {
        List<User> users = new ArrayList<>();
        users.add(new User(
                "strahsvid",
                "maximsushkin02@gmail.com",
                encoder.encode("cout<<myacc"),
                true,
                Role.MODERATOR));

        for (int i = 0; i < 100; i++) {
          users.add(new User(
                  faker.name().username(),
                  faker.internet().emailAddress(),
                  encoder.encode("password")));
        }


        List<Blog> blogs = new ArrayList<>();
        JsonConverter converter = new JsonConverter();
        Random random = new Random();
        for (int i = 0; i < 100; i++) {
          blogs.add(new Blog(
                  converter.convertToEntityAttribute(
                          faker.expression(faker
                                  .expression("#{json 'person','#{json ''first_name'',''#{Name.first_name}'',''last_name'',''#{Name.last_name}''}','address','#{json ''country'',''#{Address.country}'',''city'',''#{Address.city}''}'}"))),
                  users.get(random.ints(0, users.size()).findFirst().getAsInt()))
          );
        }

        List<Comment> comments = new ArrayList<>();

        for (int i = 0; i < 100; i++) {
          comments.add(new Comment(
                  converter
                          .convertToEntityAttribute(faker.expression("#{json 'comment', '#{json ''comment'', ''lorem ipsum comment comment comment''}'}")),
                  users.get(random.ints(0, users.size()).findFirst().getAsInt()),
                  blogs.get(random.ints(0, blogs.size()).findFirst().getAsInt())));
        }

        List<LikeComment> likeComments = new ArrayList<>();


        for (int i = 0; i < 100; i++) {
          likeComments.add(new LikeComment(comments.get(
                          random.ints(0, comments.size()).findFirst().getAsInt()),
                          users.get(random.ints(0, users.size()).findFirst().getAsInt())
                  )
          );
        }

        List<BlogLike> blogLikes = new ArrayList<>();


        for (int i = 0; i < 100; i++) {
          blogLikes.add(new BlogLike(blogs.get(random.ints(0, blogs.size()).findFirst().getAsInt()), users.get(random.ints(0, users.size()).findFirst().getAsInt())));
        }


        userRepository.saveAll(users);
        blogRepository.saveAll(blogs);
        commentRepository.saveAll(comments);
        commentLikeRepository.saveAll(likeComments);
        blogLikeRepository.saveAll(blogLikes);

      } catch (Throwable throwable) {
        System.err.println(throwable.getMessage());
      }
    };
  }

}
