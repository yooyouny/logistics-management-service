package com.sparta.company.domain.strategy.product.update;

import com.sparta.company.application.dto.company.CompanyUpdateRequest;
import com.sparta.company.application.dto.product.ProductUpdateRequest;
import com.sparta.company.domain.Company;
import com.sparta.company.domain.Product;
import org.springframework.stereotype.Component;

@Component
public interface ProductUpdateStrategy {
  Product update(ProductUpdateRequest request, Company company, Product product, Long userId);

}