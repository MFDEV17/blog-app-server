package com.mfdev.blogapp;

import com.mfdev.blogapp.entity.user.User;
import com.mfdev.blogapp.entity.user.authority.Role;
import com.mfdev.blogapp.repository.UserRepository;
import net.datafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

@SpringBootApplication
public class BlogAppApplication {

  public static void main(String[] args) {
    SpringApplication.run(BlogAppApplication.class, args);
  }

  @Bean
  CommandLineRunner runner(UserRepository userRepository) {
    Faker faker = new Faker();
    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    return args -> userRepository.saveAll(
            List.of(new User("strahsvid", "maximsushkin02@gmail.com", encoder.encode("cout<<myacc"), true, Role.MODERATOR),
                    new User(faker.name().username(), faker.internet().emailAddress(), encoder.encode("cout<<myacc")),
                    new User(faker.name().username(), faker.internet().emailAddress(), encoder.encode("cout<<myacc")),
                    new User(faker.name().username(), faker.internet().emailAddress(), encoder.encode("cout<<myacc")),
                    new User(faker.name().username(), faker.internet().emailAddress(), encoder.encode("cout<<myacc")),
                    new User(faker.name().username(), faker.internet().emailAddress(), encoder.encode("cout<<myacc")),
                    new User(faker.name().username(), faker.internet().emailAddress(), encoder.encode("cout<<myacc"))
            ));
  }

}
