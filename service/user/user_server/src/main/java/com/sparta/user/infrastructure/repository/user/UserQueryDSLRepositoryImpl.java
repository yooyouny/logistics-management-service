package com.sparta.user.infrastructure.repository.user;

import static com.sparta.user.domain.model.QUser.user;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.user.application.dto.UserInfo;
import com.sparta.user.domain.model.User;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class UserQueryDSLRepositoryImpl implements UserQueryDSLRepository {

  private final JPAQueryFactory queryFactory;

  @Override
  public Page<UserInfo> findUserInfos(Long userId, String keyword, Pageable pageable) {
    BooleanBuilder builder = new BooleanBuilder();

    // 조건 추가
    if (keyword != null && !keyword.isEmpty()) {
      builder
          .and(user.username.containsIgnoreCase(keyword)
              .or(user.email.containsIgnoreCase(keyword)));
    }

    List<User> users = queryFactory
        .selectFrom(user)
        .where(builder)
        .orderBy(getOrderSpecifiers(pageable))
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize())
        .fetch();

    long total = queryFactory
        .selectFrom(user)
        .where(builder)
        .stream().count();

    return new PageImpl<>(users.stream().map(UserInfo::create).toList(), pageable, total);
  }

  private OrderSpecifier<?>[] getOrderSpecifiers(Pageable pageable) {
    Sort sort = pageable.getSort();
    List<OrderSpecifier<?>> orderSpecifiers = new ArrayList<>();

    // Q 클래스를 사용하여 정렬 필드 지정
    sort.forEach(order -> {
      String property = order.getProperty();

      OrderSpecifier<?> orderSpecifier = switch (property) {
        case "id" -> order.isAscending() ? user.id.asc() : user.id.desc();
        case "username" -> order.isAscending() ? user.username.asc() : user.username.desc();
        default -> null; // 기본값 처리
      };

      if (orderSpecifier != null) {
        orderSpecifiers.add(orderSpecifier);
      }
    });

    return orderSpecifiers.toArray(new OrderSpecifier[0]);
  }

}
