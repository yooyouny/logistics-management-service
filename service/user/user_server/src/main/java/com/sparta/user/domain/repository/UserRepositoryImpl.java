package com.sparta.user.domain.repository;

import com.sparta.user.application.dto.UserInfo;
import com.sparta.user.domain.model.User;
import com.sparta.user.infrastructure.repository.UserJpaRepository;
import com.sparta.user.infrastructure.repository.UserQueryDSLRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class UserRepositoryImpl implements UserRepository {

  private final UserJpaRepository userJpaRepository;
  private final UserQueryDSLRepository userQueryDSLRepository;

  @Override
  public Optional<User> findByUsername(String username) {
    return userJpaRepository.findByUsername(username);
  }

  @Override
  public Optional<User> findById(Long id) {
    return userJpaRepository.findById(id);
  }

  @Override
  public User save(User user) {
    return userJpaRepository.save(user);
  }

  @Override
  public boolean existsByIdAndRoleMaster(Long userId) {
    return userJpaRepository.existsByIdAndRoleMaster(userId);
  }

  @Override
  public void deleteById(Long userId) {
    userJpaRepository.deleteById(userId);
  }

  @Override
  public Page<UserInfo> findUserInfos(Long userId, String keyword, Pageable pageable) {
    return userQueryDSLRepository.findUserInfos(userId, keyword, pageable);
  }
}
