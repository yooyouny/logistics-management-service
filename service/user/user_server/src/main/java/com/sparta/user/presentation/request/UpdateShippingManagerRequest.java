package com.sparta.user.presentation.request;

import com.sparta.user.domain.model.vo.ShippingManagerType;
import com.sparta.user.presentation.controller.aop.ValidEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record UpdateShippingManagerRequest(
    @NotBlank String slackId,
    @ValidEnum(enumClass = ShippingManagerType.class) ShippingManagerType type,
    @NotNull UUID hubId
) {

}
