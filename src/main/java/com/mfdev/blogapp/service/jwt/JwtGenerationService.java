package com.mfdev.blogapp.service.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class JwtGenerationService {
  private final JwtEncoder encoder;

  public String generateToken(Authentication auth) {
    Instant now = Instant.now();
    long expiry = 36_000L;

    JwtClaimsSet set = JwtClaimsSet
            .builder()
            .issuer("claims")
            .issuedAt(now)
            .expiresAt(now.plusSeconds(expiry))
            .subject(auth.getName())
            .claim("scope", auth.getAuthorities())
            .build();

    return encoder
            .encode(JwtEncoderParameters.from(set))
            .getTokenValue();
  }
}
