package com.sparta.company.application.mapper;

import com.sparta.company.application.dto.company.CompanyCreateRequest;
import com.sparta.company.application.dto.company.CompanyResponse;
import com.sparta.company.domain.Company;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class CompanyMapper {

  public Company createRequestToEntity(CompanyCreateRequest companyCreateRequest) {
    return Company.builder()
        .companyType(companyCreateRequest.getCompanyType())
        .hubId(companyCreateRequest.getHubId())
        .companyAddress(companyCreateRequest.getCompanyAddress())
        .companyName(companyCreateRequest.getCompanyName())
        .userId(companyCreateRequest.getUserId())
        .isDelete(false)
        .build();
  }

  public CompanyResponse toResponse(Company company) {
    CompanyResponse response = new CompanyResponse();
    response.setCompanyType(company.getCompanyType());
    response.setCompanyAddress(company.getCompanyAddress());
    response.setCompanyName(company.getCompanyName());
    response.setUserId(company.getUserId());
    response.setCompanyId(company.getCompanyId());
    response.setHubId(company.getHubId());
    return response;
  }

}
