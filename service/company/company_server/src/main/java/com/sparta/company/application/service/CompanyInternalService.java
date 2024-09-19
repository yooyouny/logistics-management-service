package com.sparta.company.application.service;

import com.sparta.commons.domain.exception.BusinessException;
import com.sparta.company.domain.Company;
import com.sparta.company.exception.CompanyErrorCode;
import com.sparta.company.infrastructure.repository.company.CompanyRepository;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CompanyInternalService {

  private final CompanyRepository companyRepository;

  public Optional<UUID> getHubIdByCompanyId(UUID companyId) {
    Company company =
        companyRepository
            .findById(companyId)
            .orElseThrow(() -> new BusinessException(CompanyErrorCode.NOT_FOUND));
    return Optional.ofNullable(company.getHubId());
  }
}
