package com.mfdev.blogapp.repository.user;

import com.mfdev.blogapp.dto.user.LoginInfoUserDTO;
import com.mfdev.blogapp.entity.user.User;
import com.mfdev.blogapp.entity.user.authority.Role;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  Optional<LoginInfoUserDTO> findUserByUsername(String username);

  @Modifying
  @Transactional
  @Query("delete from users u where u.username = ?1")
  void deleteByUsername(String username);

  @Modifying
  @Transactional
  @Query("update users u set u.isEnable = false where u.username = ?1")
  void banUser(String username);

  @Modifying
  @Transactional
  @Query("update users u set u.role = ?1 where u.username = ?2")
  void updateRole(Role role, String username);

  @Modifying
  @Transactional
  @Query("update users u set u.password = ?1 where u.username = ?2")
  void updatePassword(String password, String username);

  @Query("select u.password from users u where u.username = ?1")
  String getUserPassword(String username);

  @Modifying
  @Transactional
  @Query("update users u set u.email = ?1 where u.username = ?2")
  void updateUserEmail(String email, String username);

  @Query("select u.email from users u where u.username = ?1")
  String getUserEmail(String username);

  @Query("update users u set u.isEnable = true where u.username = ?1")
  void activateAccount(String username);

  @Query("select id from users where username = ?1")
  Long getUserId(String username);
}
