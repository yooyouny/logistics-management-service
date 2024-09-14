package com.sparta.company.domain.strategy.product.update;

import com.sparta.company.application.dto.product.ProductUpdateRequest;
import com.sparta.company.domain.Company;
import com.sparta.company.domain.Product;

public class MasterUpdateStrategy implements ProductUpdateStrategy{

  @Override
  public Product update(ProductUpdateRequest request, Company company, Product product,
      Long userId) {
    product.update(request, company);
    return product;
  }
}
