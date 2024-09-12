package com.sparta.user.presentation.request;

import com.sparta.user.domain.model.vo.ShippingManagerType;
import java.util.UUID;

public record CreateShippingManagerRequest(
    String slackId,
    ShippingManagerType type,
    UUID hubId
) {

}
