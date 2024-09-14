package com.sparta.company.domain.strategy.company.update;

import com.sparta.commons.domain.exception.BusinessException;
import com.sparta.company.application.dto.company.CompanyUpdateRequest;
import com.sparta.company.domain.Company;
import com.sparta.company.exception.CompanyErrorCode;
import com.sparta.company.infrastructure.client.UserClient;
import com.sparta.user.dto.user_dto.UserDto;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class HubCompanyUpdateStrategy implements CompanyUpdateStrategy {

  private final UserClient userClient;

  @Override
  public Company update(CompanyUpdateRequest request, Company company, String username) {
    Long userId = getUserIdFromUsername(username);

    if (userId.equals(company.getUserId())) {
      company.update(request);
      return company;
    } else {
      throw new BusinessException(CompanyErrorCode.ACCESS_DENIED);
    }
  }

  private Long getUserIdFromUsername(String username) {
    Optional<UserDto> userDto = userClient.getUserDto(username);
    UserDto user = userDto.orElseThrow(
        () -> new BusinessException(CompanyErrorCode.USER_NOT_FOUND));
    Long userId = user.userId();
    return userId;
  }
}
