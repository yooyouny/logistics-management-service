package com.sparta.company.domain.strategy.product.delete;

import com.sparta.company.application.dto.product.ProductUpdateRequest;
import com.sparta.company.domain.Company;
import com.sparta.company.domain.Product;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public interface ProductDeleteStrategy {
  void delete(Product product, Long userId, String username);

}