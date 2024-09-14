package com.sparta.company.domain.strategy.product.delete;

import com.sparta.company.application.dto.product.ProductUpdateRequest;
import com.sparta.company.domain.Company;
import com.sparta.company.domain.Product;
import com.sparta.company.domain.strategy.product.update.ProductUpdateStrategy;

public class MasterDeleteStrategy implements ProductDeleteStrategy {


  @Override
  public void delete(Product product, Long userId, String username) {
    product.delete(username);
  }
}
