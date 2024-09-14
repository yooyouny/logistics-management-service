package com.sparta.user.application.service.shipping_manager;

import com.sparta.user.domain.model.ShippingManager;
import com.sparta.user.domain.model.vo.ShippingManagerType;
import com.sparta.user.domain.repository.shipping_manager.ShippingManagerRepository;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ShippingManagerInternalService {

  private final ShippingManagerRepository shippingManagerRepository;

  public List<UUID> getDeliveryAgentIdList(ShippingManagerType shippingManagerType) {
    return shippingManagerRepository.findIdsByType(shippingManagerType);
  }
}
