package com.mfdev.blogapp.repository.verificationcode;

import com.mfdev.blogapp.entity.verificationcode.VerificationCode;
import com.mfdev.blogapp.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

public interface VerificationCodeRepository extends JpaRepository<VerificationCode, Long> {

  @Query("select u from users u left outer join VerificationCode vc on vc.user.id = u.id where vc.code = ?1")
  Optional<User> findUserByVerificationCode(UUID uuid);

  @Query("delete from VerificationCode vc where vc.code = ?1")
  @Modifying
  @Transactional
  void deleteVerificationCode(UUID verificationCode);
}
