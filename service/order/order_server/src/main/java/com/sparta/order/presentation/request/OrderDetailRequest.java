package com.sparta.order.presentation.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record OrderDetailRequest(
    @NotNull(message = "주문상품 아이디는 필수입니다")
    UUID productId,
    @NotBlank(message = "주문상품 이름은 필수입니다")
    String productName,
    @Min(value = 1, message = "주문상품의 수량은 1 이상이어야 합니다")
    int quantity,

    @Min(value = 10, message = "주문상품의 단가는 10 이상이어야 합니다")
    int unitPrice

) {}
