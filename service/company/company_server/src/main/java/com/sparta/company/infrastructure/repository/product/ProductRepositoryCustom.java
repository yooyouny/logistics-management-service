package com.sparta.company.infrastructure.repository.product;

import com.sparta.company.application.dto.product.ProductResponse;
import com.sparta.company.application.dto.product.ProductSearchCond;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductRepositoryCustom {

  Page<ProductResponse> searchProduct(Pageable validatedPageable, ProductSearchCond cond);
}
