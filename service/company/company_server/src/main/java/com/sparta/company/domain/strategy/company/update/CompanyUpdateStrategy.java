package com.sparta.company.domain.strategy.company.update;

import com.sparta.company.application.dto.company.CompanyUpdateRequest;
import com.sparta.company.domain.Company;
import org.springframework.stereotype.Component;

@Component
public interface CompanyUpdateStrategy {
  Company update(CompanyUpdateRequest request, Company company, String username);

}
