package com.sparta.user.presentation.request;

import com.sparta.user.domain.model.vo.UserRole;

public record UpdateAuthorityRequest(
    UserRole role
) {

}
