package com.sparta.company.infrastructure.repository;

import static com.sparta.company.domain.QCompany.*;
import static org.springframework.util.StringUtils.*;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.company.application.dto.CompanyResponse;
import com.sparta.company.application.dto.CompanySearchCond;
import com.sparta.company.application.mapper.CompanyMapper;
import com.sparta.company.domain.Company;
import com.sparta.company.domain.CompanyType;
import com.sparta.company.domain.QCompany;
import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.util.StringUtils;


public class CompanyRepositoryCustomImpl implements CompanyRepositoryCustom {

  private final EntityManager em;
  private final JPAQueryFactory queryFactory;
  private final CompanyMapper companyMapper;

  public CompanyRepositoryCustomImpl(EntityManager em) {
    this.em = em;
    this.queryFactory = new JPAQueryFactory(em);
    this.companyMapper = new CompanyMapper();
  }

  @Override
  public Page<CompanyResponse> searchCompany(Pageable pageable, CompanySearchCond cond) {
    JPAQuery<Company> query = queryFactory
        .selectFrom(company)
        .where(
            companyNameLike(cond.getCompanyName()),
            hubIdEq(cond.getHubId()),
            companyTypeEq(cond.getCompanyType())
        );

    List<CompanyResponse> content = query
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize()).fetch()
        .stream()
        .map(companyMapper::toResponse).toList();

    return PageableExecutionUtils.getPage(content, pageable, query::fetchCount);
  }

  public BooleanExpression companyNameLike(String companyName) {
    return hasText(companyName) ? company.companyName.like("%" + companyName + "%") : null;
  }

  public BooleanExpression hubIdEq(UUID hubId) {
    return hubId != null ? company.hubId.eq(hubId) : null;
  }

  public BooleanExpression companyTypeEq(CompanyType companyType) {
    return companyType != null ? company.companyType.eq(companyType) : null;
  }
}
