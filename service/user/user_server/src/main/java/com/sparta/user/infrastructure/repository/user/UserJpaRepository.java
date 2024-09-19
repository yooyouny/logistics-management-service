package com.sparta.user.infrastructure.repository.user;

import com.sparta.user.domain.model.User;
import java.util.Optional;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserJpaRepository extends JpaRepository<User, Long> {

  Optional<User> findByUsername(String username);

  @Query(
      "SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM User u WHERE u.id = :userId AND u.role = 'ROLE_MASTER'")
  boolean existsByIdAndRoleMaster(Long userId);

  void deleteById(@NonNull Long id);
}
