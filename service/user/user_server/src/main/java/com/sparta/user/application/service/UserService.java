package com.sparta.user.application.service;

import com.sparta.commons.domain.exception.BusinessException;
import com.sparta.user.application.dto.UserInfo;
import com.sparta.user.domain.repository.UserRepository;
import com.sparta.user.exception.UserErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserService {

  private final UserRepository userRepository;

  @Transactional(readOnly = true)
  public UserInfo getUserInfo(Long userId) {
    return userRepository.findById(userId)
        .map(UserInfo::create)
        .orElseThrow(() -> new BusinessException(UserErrorCode.USER_NOT_FOUND));
  }
}
