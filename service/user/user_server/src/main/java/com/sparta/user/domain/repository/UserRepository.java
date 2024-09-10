package com.sparta.user.domain.repository;

import com.sparta.user.domain.model.User;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository {

  Optional<User> findByUsername(String username);

  User save(User user);
}
