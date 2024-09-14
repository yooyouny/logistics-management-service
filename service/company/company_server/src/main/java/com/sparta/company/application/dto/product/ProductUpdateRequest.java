package com.sparta.company.application.dto.product;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import lombok.Data;

@Data
public class ProductUpdateRequest {
  @NotNull
  private UUID companyId;

  @NotNull
  private UUID hubId;

  @NotBlank
  private String productName;

  private String productDescription;

  @NotNull
  @Min(value = 0, message = "가격은 -가 될 수 없습니다.")
  private Integer productPrice;

  @NotNull
  @Min(value = 0, message = "수량은 -가 될 수 없습니다.")
  private Integer stockQuantity;
}
