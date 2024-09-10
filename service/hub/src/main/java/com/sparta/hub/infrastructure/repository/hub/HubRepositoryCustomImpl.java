package com.sparta.hub.infrastructure.repository.hub;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.hub.application.dto.hub.HubResponse;
import com.sparta.hub.application.dto.hub.HubSearchCond;
import com.sparta.hub.application.mapper.HubMapper;
import com.sparta.hub.domain.Hub;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.util.StringUtils;

import java.util.List;

import static com.sparta.hub.domain.QHub.*;

public class HubRepositoryCustomImpl implements HubRepositoryCustom {

  private final EntityManager em;
  private final JPAQueryFactory queryFactory;
  private final HubMapper hubMapper;

  public HubRepositoryCustomImpl(EntityManager em) {
    this.em = em;
    this.queryFactory = new JPAQueryFactory(em);
    this.hubMapper = new HubMapper();
  }

  @Override
  public Page<HubResponse> searchHub(Pageable pageable, HubSearchCond cond) {
    JPAQuery<Hub> common = queryFactory
        .selectFrom(hub)
        .where(
            hubNameEq(cond.getName()),
            hubAddressEq(cond.getAddress()),
            hub.isDelete.eq(false)
        );

    List<Hub> list = common.offset(pageable.getOffset())
        .limit(pageable.getPageSize()).fetch();

    List<HubResponse> content = list.stream()
        .map(hubMapper::toResponse).toList();
    return PageableExecutionUtils.getPage(content, pageable, common::fetchCount);
  }

  private BooleanExpression hubNameEq(String name) {
    return StringUtils.hasText(name) ? hub.hubName.like("%" + name + "%") : null;
  }

  private BooleanExpression hubAddressEq(String address) {
    return StringUtils.hasText(address) ? hub.hubAddress.like("%" + address + "%") : null;
  }
}
