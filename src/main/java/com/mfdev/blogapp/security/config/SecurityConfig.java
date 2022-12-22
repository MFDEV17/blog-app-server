package com.mfdev.blogapp.security.config;

import lombok.SneakyThrows;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationEntryPoint;
import org.springframework.security.oauth2.server.resource.web.access.BearerTokenAccessDeniedHandler;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

  @Bean
  PasswordEncoder encoder() {
    return new BCryptPasswordEncoder(10);
  }

  @Bean
  @SneakyThrows
  public SecurityFilterChain securityFilterChain(HttpSecurity http) {
    http.csrf().disable();
    http.cors().disable();

    http.httpBasic(Customizer.withDefaults());
    http.oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt);
    http.sessionManagement(session ->
            session.sessionCreationPolicy(STATELESS));
    http.exceptionHandling()
            .authenticationEntryPoint(new BearerTokenAuthenticationEntryPoint())
            .accessDeniedHandler(new BearerTokenAccessDeniedHandler());
    return http.build();
  }
}
