package com.sparta.user.infrastructure.repository.shipping_manager;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.user.application.dto.SippingManagerInfo;
import com.sparta.user.domain.model.QShippingManager;
import com.sparta.user.domain.model.ShippingManager;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class ShippingManagerQueryDSLRepositoryImpl implements ShippingManagerQueryDSLRepository {

  private final JPAQueryFactory queryFactory;

  @Override
  public Page<SippingManagerInfo> findShippingManagerInfos(
      UUID shippingManagerId, String keyword, Pageable pageable) {
    QShippingManager shippingManager = QShippingManager.shippingManager;

    JPAQuery<ShippingManager> query = queryFactory.select(shippingManager).from(shippingManager);

    // shippingManagerId가 존재하는 경우 == ROLE_HUB_MANAGER
    if (shippingManagerId != null) {
      UUID hubId =
          queryFactory
              .select(shippingManager.hubId)
              .from(shippingManager)
              .where(shippingManager.id.eq(shippingManagerId))
              .fetchOne();

      query.where(shippingManager.hubId.eq(hubId));
    }

    // keyword가 존재할 경우 필터링
    if (keyword != null && !keyword.isEmpty()) {
      query.where(
          shippingManager
              .slackId
              .containsIgnoreCase(keyword)
              .or(shippingManager.type.stringValue().containsIgnoreCase(keyword)));
    }

    // 페이징 처리
    List<ShippingManager> results =
        query.offset(pageable.getOffset()).limit(pageable.getPageSize()).fetch();

    long total = query.stream().count();

    // SippingManager를 SippingManagerInfo로 변환
    List<SippingManagerInfo> infoResults =
        results.stream().map(SippingManagerInfo::create).toList();

    return new PageImpl<>(infoResults, pageable, total);
  }
}
