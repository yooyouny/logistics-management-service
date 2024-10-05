package com.sparta.company.infrastructure.repository.company;

import com.sparta.company.application.dto.company.CompanyResponse;
import com.sparta.company.application.dto.company.CompanySearchCond;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CompanyRepositoryCustom {

  Page<CompanyResponse> searchCompany(Pageable pageable, CompanySearchCond cond);
}
