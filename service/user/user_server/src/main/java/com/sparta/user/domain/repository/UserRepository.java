package com.sparta.user.domain.repository;

import com.sparta.user.application.dto.UserInfo;
import com.sparta.user.domain.model.User;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository {

  Optional<User> findByUsername(String username);

  Optional<User> findById(Long id);

  User save(User user);

  boolean existsByIdAndRoleMaster(Long userId);

  void deleteById(Long userId);

  Page<UserInfo> findUserInfos(Long userId, String keyword, Pageable pageable);
}
