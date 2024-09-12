package com.sparta.user.domain.repository.shipping_manager;

import com.sparta.user.application.dto.SippingManagerInfo;
import com.sparta.user.domain.model.ShippingManager;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface ShippingManagerRepository {

  Optional<ShippingManager> findById(UUID shippingManagerId);

  Page<SippingManagerInfo> findShippingManagerInfos(UUID shippingManagerId, String keyword, Pageable pageable);

  boolean existsBySlackId(String slackId);
}
