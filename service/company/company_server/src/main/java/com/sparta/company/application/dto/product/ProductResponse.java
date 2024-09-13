package com.sparta.company.application.dto.product;

import java.util.UUID;
import lombok.Data;

@Data
public class ProductResponse {

  private UUID productId;
  private UUID companyId;
  private UUID hubId;
  private String productName;
  private String productDescription;
  private Integer productPrice;
  private Integer stockQuantity;

}
