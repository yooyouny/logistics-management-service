package com.sparta.user.infrastructure.repository;

import com.sparta.user.domain.model.User;
import com.sparta.user.domain.repository.UserRepository;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepositoryImpl extends JpaRepository<User, Long>, UserRepository {

  @Override
  Optional<User> findByUsername(String username);

  @Override
  @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM User u WHERE u.id = :userId AND u.role = 'ROLE_MASTER'")
  boolean existsByIdAndRoleMaster(Long userId);
}
