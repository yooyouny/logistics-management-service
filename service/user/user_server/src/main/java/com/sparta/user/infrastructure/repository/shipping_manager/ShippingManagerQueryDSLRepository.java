package com.sparta.user.infrastructure.repository.shipping_manager;

import com.sparta.user.application.dto.SippingManagerInfo;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface ShippingManagerQueryDSLRepository {

  Page<SippingManagerInfo> findShippingManagerInfos(UUID shippingManagerId, String keyword, Pageable pageable);
}
