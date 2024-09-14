package com.sparta.user.domain.repository.shipping_manager;

import com.sparta.user.application.dto.SippingManagerInfo;
import com.sparta.user.domain.model.ShippingManager;
import com.sparta.user.domain.model.vo.ShippingManagerType;
import com.sparta.user.infrastructure.repository.shipping_manager.ShippingManagerJpaRepository;
import com.sparta.user.infrastructure.repository.shipping_manager.ShippingManagerQueryDSLRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class ShippingManagerRepositoryImpl implements ShippingManagerRepository {

  private final ShippingManagerJpaRepository shippingManagerJpaRepository;
  private final ShippingManagerQueryDSLRepository shippingManagerQueryDSLRepository;

  @Override
  public Optional<ShippingManager> findById(UUID shippingManagerId) {
    return shippingManagerJpaRepository.findById(shippingManagerId);
  }

  @Override
  public Page<SippingManagerInfo> findShippingManagerInfos(UUID shippingManagerId, String keyword,
      Pageable pageable) {
    return shippingManagerQueryDSLRepository.findShippingManagerInfos(shippingManagerId, keyword, pageable);
  }

  @Override
  public boolean existsBySlackId(String slackId) {
    return shippingManagerJpaRepository.existsBySlackId(slackId);
  }

  @Override
  public List<UUID> findIdsByType(ShippingManagerType shippingManagerType) {
    return shippingManagerJpaRepository.findIdsByType(shippingManagerType);
  }
}
