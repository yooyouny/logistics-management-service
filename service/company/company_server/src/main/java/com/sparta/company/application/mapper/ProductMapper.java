package com.sparta.company.application.mapper;

import com.sparta.company.application.dto.product.ProductCreateRequest;
import com.sparta.company.application.dto.product.ProductResponse;
import com.sparta.company.domain.Company;
import com.sparta.company.domain.Product;
import lombok.Data;

@Data
public class ProductMapper {

  public Product createDtoToEntity(ProductCreateRequest request, Company company) {
    return Product.builder()
        .productName(request.getProductName())
        .productPrice(request.getProductPrice())
        .company(company)
        .productDescription(request.getProductDescription())
        .stockQuantity(request.getStockQuantity())
        .hubId(request.getHubId())
        .isDelete(false)
        .build();
  }

  public ProductResponse toResponse(Product product) {
    ProductResponse response = new ProductResponse();
    response.setProductName(product.getProductName());
    response.setProductPrice(product.getProductPrice());
    response.setProductDescription(product.getProductDescription());
    response.setStockQuantity(product.getStockQuantity());
    response.setCompanyId(product.getCompany().getCompanyId());
    response.setProductId(product.getProductId());
    response.setHubId(product.getHubId());
    return response;
  }
}
