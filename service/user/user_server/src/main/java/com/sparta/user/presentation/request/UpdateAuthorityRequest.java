package com.sparta.user.presentation.request;

import com.sparta.user.domain.model.vo.UserRole;
import com.sparta.user.presentation.controller.aop.ValidEnum;

public record UpdateAuthorityRequest(@ValidEnum(enumClass = UserRole.class) UserRole role) {}
