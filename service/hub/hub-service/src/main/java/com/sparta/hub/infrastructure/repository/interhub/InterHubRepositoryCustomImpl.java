package com.sparta.hub.infrastructure.repository.interhub;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.hub.application.dto.interhub.InterHubSearchCond;
import com.sparta.hub.application.mapper.InterHubMapper;
import com.sparta.hub.domain.InterHub;
import com.sparta.hub.domain.QHub;
import com.sparta.hub.dto.InterHubResponse;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.UUID;

import static com.sparta.hub.domain.QHub.*;
import static com.sparta.hub.domain.QInterHub.*;


public class InterHubRepositoryCustomImpl implements InterHubRepositoryCustom {

  private final EntityManager em;
  private final JPAQueryFactory queryFactory;
  private final InterHubMapper interHubMapper;

  public InterHubRepositoryCustomImpl(EntityManager em, InterHubMapper interHubMapper) {
    this.em = em;
    this.queryFactory = new JPAQueryFactory(em);
    this.interHubMapper = interHubMapper;
  }

  @Override
  public Page<InterHubResponse> searchHub(Pageable pageable, InterHubSearchCond cond) {
    QHub hub2 = new QHub("hub2");
    JPAQuery<InterHub> commonQuery = queryFactory
        .selectFrom(interHub)
        .join(interHub.departureHub, hub).fetchJoin()
        .join(interHub.arrivalHub, hub2).fetchJoin()
        .where(
            departureHubEq(cond.getDepartureHubId()),
            arrivalHubEq(cond.getArrivalHubId()),
            departureHubNameLike(cond.getDepartureHubName()),
            arrivalHubNameLike(cond.getArrivalHubName()),
            interHub.isDelete.eq(false)
        );
    List<InterHub> list = commonQuery
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize())
        .fetch();

    List<InterHubResponse> content = list.stream()
        .map(interHubMapper::toResponse).toList();
    return PageableExecutionUtils.getPage(content, pageable, commonQuery::fetchCount);
  }

  private BooleanExpression departureHubEq(UUID departureHubId) {
    return departureHubId != null ? interHub.departureHub.hubId.eq(departureHubId) : null;
  }

  private BooleanExpression arrivalHubEq(UUID arrivalHubId) {
    return arrivalHubId != null ? interHub.departureHub.hubId.eq(arrivalHubId) : null;
  }

  private BooleanExpression departureHubNameLike(String departureHubName) {
    return StringUtils.hasText(departureHubName) ? interHub.departureHub.hubName.like(
        "%" + departureHubName + "%") : null;
  }

  private BooleanExpression arrivalHubNameLike(String arrivalHubName) {
    return StringUtils.hasText(arrivalHubName) ? interHub.arrivalHub.hubName.like(
        "%" + arrivalHubName + "%") : null;
  }

}
