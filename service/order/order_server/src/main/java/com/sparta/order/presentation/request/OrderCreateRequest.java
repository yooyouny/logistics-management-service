package com.sparta.order.presentation.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

public record OrderCreateRequest(
    @NotNull(message = "요청업체아이디는 필수입니다") UUID supplierCompanyId,
    @NotNull(message = "수령업체아이디는 필수입니다") UUID receiverCompanyId,
    @NotNull(message = "관리허브아이디는 필수입니다") UUID managementHubId,
    @Valid @NotNull(message = "주문상품은 필수입니다") List<OrderDetailRequest> orderDetails,
    @NotNull(message = "수령인아이디는 필수입니다") UUID shippingManagerId,
    @NotBlank(message = "수령인의 슬랙아이디는 필수입니다") String shippingManagerSlackId,
    @NotBlank(message = "배송지 주소는 필수입니다") String shippingAddress) {}
