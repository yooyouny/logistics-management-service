package com.sparta.delivery.infrastructure.repository;

import static com.sparta.delivery.domain.model.QDelivery.delivery;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.delivery.domain.model.Delivery;
import com.sparta.delivery.domain.model.State.DeliveryState;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class DeliveryRepositoryImpl implements DeliveryRepositoryCustom {
  private final JPAQueryFactory queryFactory;

  @Override
  public List<Delivery> findDeliveries(UUID shippingManagerId, LocalDateTime requestedDateTime) {
    return queryFactory
        .selectFrom(delivery)
        .where(
            delivery.shippingManagerId.eq(shippingManagerId), // 배송 담당자 필터링
            delivery.shippingStartDate.loe(requestedDateTime), // 요청된 날짜보다 이전 또는 같은 날짜
            delivery.deliveryState.eq(DeliveryState.REQUESTED) // 배송 상태가 REQUESTED
        )
        .fetch();
  }
}
