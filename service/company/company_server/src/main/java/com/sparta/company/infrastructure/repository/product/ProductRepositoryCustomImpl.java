package com.sparta.company.infrastructure.repository.product;

import static com.sparta.company.domain.QProduct.*;
import static org.springframework.util.StringUtils.hasText;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.company.application.dto.product.ProductResponse;
import com.sparta.company.application.dto.product.ProductSearchCond;
import com.sparta.company.application.mapper.ProductMapper;
import com.sparta.company.domain.Product;
import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

public class ProductRepositoryCustomImpl implements ProductRepositoryCustom {

  private final EntityManager em;
  private final JPAQueryFactory queryFactory;
  private final ProductMapper productMapper;

  public ProductRepositoryCustomImpl(EntityManager em) {
    this.em = em;
    this.queryFactory = new JPAQueryFactory(em);
    productMapper = new ProductMapper();
  }

  @Override
  public Page<ProductResponse> searchProduct(Pageable validatedPageable, ProductSearchCond cond) {
    JPAQuery<Product> commonQuery = queryFactory
        .selectFrom(product)
        .where(
            productNameLike(cond.getProductName()),
            hubIdEq(cond.getHubId()),
            companyIdEq(cond.getCompanyId())
        );

    List<ProductResponse> content = commonQuery
        .limit(validatedPageable.getPageSize())
        .offset(validatedPageable.getOffset())
        .fetch()
        .stream()
        .map(productMapper::toResponse).toList();

    return PageableExecutionUtils.getPage(content, validatedPageable, commonQuery::fetchCount);
  }

  public BooleanExpression productNameLike(String productName) {
    return hasText(productName) ? product.productName.like("%" + productName + "%") : null;
  }

  public BooleanExpression hubIdEq(UUID hubId) {
    return hubId != null ? product.hubId.eq(hubId) : null;
  }

  public BooleanExpression companyIdEq(UUID companyId) {
    return companyId != null ? product.company.companyId.eq(companyId) : null;
  }

}
