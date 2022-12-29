package com.mfdev.blogapp.security.details;

import com.mfdev.blogapp.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
  private final UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return new UserDetailsImpl(
            userRepository
                    .findUserByUsername(username)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"))
    );
  }
}
