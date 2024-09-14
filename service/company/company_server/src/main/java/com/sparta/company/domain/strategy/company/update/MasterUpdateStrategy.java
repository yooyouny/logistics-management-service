package com.sparta.company.domain.strategy.company.update;

import com.sparta.company.application.dto.company.CompanyUpdateRequest;
import com.sparta.company.domain.Company;

public class MasterUpdateStrategy implements CompanyUpdateStrategy{

  @Override
  public Company update(CompanyUpdateRequest request, Company company, String username, Long userId) {
    company.update(request);
    return company;
  }
}
