package com.sparta.user.application.service;

import static com.sparta.user.exception.ErrorCode.USER_CONFLICT;
import static com.sparta.user.exception.ErrorCode.USER_NOT_FOUND;

import com.sparta.user.domain.model.User;
import com.sparta.user.domain.repository.UserRepository;
import com.sparta.user.dto.user_dto.UserDto;
import com.sparta.user.exception.BusinessException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserInternalService {
  private final UserRepository userRepository;

  @Transactional
  public void createUser(String username, String email, String encodedPassword) {
    userRepository.findByUsername(username).ifPresent(user -> {
      throw new BusinessException(USER_CONFLICT);
    });
    userRepository.save(User.create(username, email, encodedPassword));
  }

  @Transactional(readOnly = true)
  public Optional<UserDto> getUserDto(String username) {
    User savedUser = userRepository.findByUsername(username)
        .orElseThrow(() -> new BusinessException(USER_NOT_FOUND));
    return Optional.of(new UserDto(
        savedUser.getId(),
        savedUser.getUsername(),
        savedUser.getEmail(),
        savedUser.getPassword(),
        savedUser.getRole().name(),
        savedUser.getIsDelete()
    ));
  }

  @Transactional(readOnly = true)
  public boolean isMaster(Long userId) {
    return userRepository.existsByIdAndRoleMaster(userId);
  }
}
