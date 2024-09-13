package com.sparta.company.application.mapper;

import com.sparta.company.application.dto.CompanyRequest;
import com.sparta.company.application.dto.CompanyResponse;
import com.sparta.company.domain.Company;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class CompanyMapper {

  public Company createRequestToEntity(CompanyRequest companyRequest) {
    return Company.builder()
        .companyType(companyRequest.getCompanyType())
        .HubId(companyRequest.getHubId())
        .companyAddress(companyRequest.getCompanyAddress())
        .companyName(companyRequest.getCompanyName())
        .userId(companyRequest.getUserId())
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
