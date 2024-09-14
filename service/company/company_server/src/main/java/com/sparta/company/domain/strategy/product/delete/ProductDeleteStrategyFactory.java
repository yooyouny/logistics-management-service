package com.sparta.company.domain.strategy.product.delete;

import com.sparta.commons.domain.exception.BusinessException;
import com.sparta.company.exception.CompanyErrorCode;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ProductDeleteStrategyFactory {

  public ProductDeleteStrategy createStrategy(String role) {
    return switch (role) {
      case "ROLE_HUB_COMPANY" -> new HubCompanyDeleteStrategy();
      case "ROLE_MASTER" -> new MasterDeleteStrategy();
      default -> throw new BusinessException(CompanyErrorCode.ACCESS_DENIED);
    };
  }
}

