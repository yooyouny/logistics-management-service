package com.sparta.company.domain.strategy.product.delete;

import com.sparta.company.domain.Product;
import org.springframework.stereotype.Component;

@Component
public interface ProductDeleteStrategy {
  void delete(Product product, Long userId, String username);

}