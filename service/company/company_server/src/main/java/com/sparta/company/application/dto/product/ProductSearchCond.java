package com.sparta.company.application.dto.product;

import java.util.UUID;
import lombok.Data;

@Data
public class ProductSearchCond {

  private String productName;
  private UUID hubId;
  private UUID companyId;
}
