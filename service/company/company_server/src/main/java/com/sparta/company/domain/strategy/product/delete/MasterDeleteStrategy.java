package com.sparta.company.domain.strategy.product.delete;

import com.sparta.company.domain.Product;

public class MasterDeleteStrategy implements ProductDeleteStrategy {


  @Override
  public void delete(Product product, Long userId, String username) {
    product.delete(username);
  }
}
