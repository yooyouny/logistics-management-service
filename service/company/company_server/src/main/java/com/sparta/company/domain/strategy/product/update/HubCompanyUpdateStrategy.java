package com.sparta.company.domain.strategy.product.update;

import com.sparta.commons.domain.exception.BusinessException;
import com.sparta.company.application.dto.company.CompanyUpdateRequest;
import com.sparta.company.application.dto.product.ProductUpdateRequest;
import com.sparta.company.domain.Company;
import com.sparta.company.domain.Product;
import com.sparta.company.domain.strategy.company.update.CompanyUpdateStrategy;
import com.sparta.company.exception.ProductErrorCode;

public class HubCompanyUpdateStrategy implements ProductUpdateStrategy {

  @Override
  public Product update(ProductUpdateRequest request, Company company, Product product,
      Long userId) {
     if(company.getUserId().equals(userId)) {
       product.update(request,company);
       return product;
     }else{
       throw new BusinessException(ProductErrorCode.ACCESS_DENIED);
     }
  }
}
