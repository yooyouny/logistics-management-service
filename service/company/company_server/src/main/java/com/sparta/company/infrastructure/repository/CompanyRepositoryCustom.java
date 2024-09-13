package com.sparta.company.infrastructure.repository;

import com.sparta.company.application.dto.CompanyResponse;
import com.sparta.company.application.dto.CompanySearchCond;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CompanyRepositoryCustom {

  Page<CompanyResponse> searchCompany(Pageable pageable, CompanySearchCond cond);
}
