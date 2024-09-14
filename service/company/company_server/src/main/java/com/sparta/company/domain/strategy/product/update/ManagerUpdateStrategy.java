package com.sparta.company.domain.strategy.product.update;

import com.sparta.commons.domain.exception.BusinessException;
import com.sparta.company.application.dto.product.ProductUpdateRequest;
import com.sparta.company.domain.Company;
import com.sparta.company.domain.Product;
import com.sparta.company.exception.CompanyErrorCode;
import com.sparta.company.exception.ProductErrorCode;
import com.sparta.company.infrastructure.client.HubClient;
import com.sparta.hub.dto.HubResponse;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ManagerUpdateStrategy implements ProductUpdateStrategy {

  private final HubClient hubClient;

  @Override
  public Product update(ProductUpdateRequest request, Company company, Product product,
      Long userId) {
    Optional<HubResponse> hubResponse = hubClient.getHubByCompany(company.getHubId());
    HubResponse response = hubResponse.orElseThrow(
        () -> new BusinessException(CompanyErrorCode.NOT_FOUND));
    if (response.getUserId() == userId && response.getHubId().equals(company.getHubId())) {
      product.update(request, company);
      return product;
    } else {
      throw new BusinessException(ProductErrorCode.ACCESS_DENIED);
    }

  }

}
