package com.sparta.company.domain.strategy.product.update;

import com.sparta.commons.domain.exception.BusinessException;
import com.sparta.company.exception.CompanyErrorCode;
import com.sparta.company.infrastructure.client.HubClient;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ProductUpdateStrategyFactory {

  private final HubClient hubClient;

  public ProductUpdateStrategy createStrategy(String role) {
    return switch (role) {
      case "ROLE_HUB_MANAGER" -> new ManagerUpdateStrategy(hubClient);
      case "ROLE_HUB_COMPANY" -> new HubCompanyUpdateStrategy();
      case "ROLE_MASTER" -> new MasterUpdateStrategy();
      default -> throw new BusinessException(CompanyErrorCode.ACCESS_DENIED);
    };
  }
}
