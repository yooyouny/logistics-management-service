package com.sparta.user.infrastructure.repository;

import com.sparta.user.application.dto.UserInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface UserQueryDSLRepository {

  Page<UserInfo> findUserInfos(Long userId, String keyword, Pageable pageable);
}
