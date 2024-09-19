package com.sparta.company.domain.strategy.company.update;

import com.sparta.commons.domain.exception.BusinessException;
import com.sparta.company.exception.CompanyErrorCode;
import com.sparta.company.infrastructure.client.HubClient;
import com.sparta.company.infrastructure.client.UserClient;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CompanyUpdateStrategyFactory {

  private final HubClient hubClient;
  private final UserClient userClient;

  public CompanyUpdateStrategy createStrategy(String role) {
    return switch (role) {
      case "ROLE_HUB_MANAGER" -> new ManagerUpdateStrategy(hubClient, userClient);
      case "ROLE_HUB_COMPANY" -> new HubCompanyUpdateStrategy(userClient);
      case "ROLE_MASTER" -> new MasterUpdateStrategy();
      default -> throw new BusinessException(CompanyErrorCode.ACCESS_DENIED);
    };
  }
}
