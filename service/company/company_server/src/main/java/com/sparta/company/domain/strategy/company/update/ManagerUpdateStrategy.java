package com.sparta.company.domain.strategy.company.update;

import com.sparta.commons.domain.exception.BusinessException;
import com.sparta.company.application.dto.company.CompanyUpdateRequest;
import com.sparta.company.domain.Company;
import com.sparta.company.exception.CompanyErrorCode;
import com.sparta.company.infrastructure.client.HubClient;
import com.sparta.company.infrastructure.client.UserClient;
import com.sparta.hub.dto.HubResponse;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ManagerUpdateStrategy implements CompanyUpdateStrategy {

  private final HubClient hubClient;
  private final UserClient userClient;

  @Override
  public Company update(
      CompanyUpdateRequest request, Company company, String username, Long userId) {
    Optional<HubResponse> hubResponse = hubClient.getHubByCompany(request.getHubId());
    HubResponse response =
        hubResponse.orElseThrow(() -> new BusinessException(CompanyErrorCode.NOT_FOUND));
    if (response.getUserId().equals(userId)) {
      company.update(request);
      return company;
    } else {
      throw new BusinessException(CompanyErrorCode.ACCESS_DENIED);
    }
  }
}
