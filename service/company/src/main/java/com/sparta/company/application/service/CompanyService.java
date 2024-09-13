package com.sparta.company.application.service;

import com.sparta.company.application.dto.CompanyCreateRequest;
import com.sparta.company.application.dto.CompanyResponse;
import com.sparta.company.application.dto.CompanySearchCond;
import com.sparta.company.application.dto.CompanyUpdateRequest;
import com.sparta.company.application.mapper.CompanyMapper;
import com.sparta.company.domain.Company;
import com.sparta.company.exception.HubNotFoundException;
import com.sparta.company.infrastructure.client.HubClient;
import com.sparta.company.infrastructure.repository.CompanyRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CompanyService {

  private final CompanyRepository companyRepository;
  private final HubClient hubClient;
  private final CompanyMapper companyMapper = new CompanyMapper();

  public CompanyResponse createCompany(CompanyCreateRequest companyCreateRequest) {
    ResponseEntity<Void> clientResponse = hubClient.checkHubExists(companyCreateRequest.getHubId());
    if (clientResponse.getStatusCode() == HttpStatus.NOT_FOUND) {
      throw new HubNotFoundException("해당 허브 Id가 존재하지 않습니다");
    }
    Company company = companyMapper.createRequestToEntity(companyCreateRequest);
    companyRepository.save(company);
    return companyMapper.toResponse(company);
  }

  public CompanyResponse updateCompany(CompanyUpdateRequest request, UUID companyId) {
    ResponseEntity<Void> clientResponse = hubClient.checkHubExists(request.getHubId());
    if (clientResponse.getStatusCode() == HttpStatus.NOT_FOUND) {
      throw new HubNotFoundException("해당 허브 Id가 존재하지 않습니다");
    }
    Company company = companyRepository.findById(companyId).orElseThrow(
        () -> new EntityNotFoundException("해당 회사 id가 존재하지 않습니다")
    );
    company.update(request);
    return companyMapper.toResponse(company);
  }

  public void deleteCompany(UUID companyId) {
    Company company = companyRepository.findById(companyId).orElseThrow(
        () -> new EntityNotFoundException("해당 회사 id가 존재하지 않습니다")
    );
    company.delete();
  }

  @Transactional(readOnly = true)
  public CompanyResponse findOneCompany(UUID companyId) {
    Company company = companyRepository.findById(companyId).orElseThrow(
        () -> new EntityNotFoundException("해당 회사 id가 존재하지 않습니다")
    );
    return companyMapper.toResponse(company);
  }

  @Transactional(readOnly = true)
  public Page<CompanyResponse> findAllCompanies(Pageable pageable, CompanySearchCond cond) {
    Page<CompanyResponse> response = companyRepository.searchCompany(pageable, cond);
    if (response == null) {
      throw new EntityNotFoundException("해당하는 업체가 존재하지 않습니다");
    }
    return response;
  }
}
