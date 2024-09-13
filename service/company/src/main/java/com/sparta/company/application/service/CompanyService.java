package com.sparta.company.application.service;

import com.sparta.company.application.dto.CompanyRequest;
import com.sparta.company.application.dto.CompanyResponse;
import com.sparta.company.application.mapper.CompanyMapper;
import com.sparta.company.domain.Company;
import com.sparta.company.exception.HubNotFoundException;
import com.sparta.company.infrastructure.client.HubClient;
import com.sparta.company.infrastructure.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CompanyService {

  private final CompanyRepository companyRepository;
  private final HubClient hubClient;
  private final CompanyMapper companyMapper = new CompanyMapper();

  public CompanyResponse createCompany(CompanyRequest companyRequest) {
    System.out.println("companyRequest.getHubId() = " + companyRequest.getHubId());
    ResponseEntity<Void> clientResponse = hubClient.checkHubExists(companyRequest.getHubId());
    if (clientResponse.getStatusCode() == HttpStatus.NOT_FOUND) {
      throw new HubNotFoundException("해당 허브 Id가 존재하지 않습니다");
    }
    Company company = companyMapper.createRequestToEntity(companyRequest);
    companyRepository.save(company);
    return companyMapper.toResponse(company);
  }
}
