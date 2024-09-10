package com.sparta.user.infrastructure.repository;

import com.sparta.user.domain.model.User;
import com.sparta.user.domain.repository.UserRepository;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepositoryImpl extends JpaRepository<User, Long>, UserRepository {
  @Override
  Optional<User> findByUsername(String username);
}
