package com.sparta.company.domain.strategy.product.delete;

import com.sparta.commons.domain.exception.BusinessException;
import com.sparta.company.domain.Product;
import com.sparta.company.exception.ProductErrorCode;

public class HubCompanyDeleteStrategy implements ProductDeleteStrategy {

  @Override
  public void delete(Product product, Long userId, String username) {
    if (product.getCompany().getUserId().equals(userId)) {
      product.delete(username);
    } else {
      throw new BusinessException(ProductErrorCode.ACCESS_DENIED);
    }
  }
}
