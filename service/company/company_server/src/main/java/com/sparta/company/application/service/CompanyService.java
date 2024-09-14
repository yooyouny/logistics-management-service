package com.sparta.company.application.service;

import com.sparta.company.application.dto.company.CompanyCreateRequest;
import com.sparta.company.application.dto.company.CompanyResponse;
import com.sparta.company.application.dto.company.CompanySearchCond;
import com.sparta.company.application.dto.company.CompanyUpdateRequest;
import com.sparta.company.application.mapper.CompanyMapper;
import com.sparta.company.domain.Company;
import com.sparta.company.exception.HubNotFoundException;
import com.sparta.company.infrastructure.client.HubClient;
import com.sparta.company.infrastructure.repository.company.CompanyRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
    boolean checkHub = hubClient.checkHubExists(companyCreateRequest.getHubId());
    if (!checkHub) {
      throw new HubNotFoundException("해당 허브 Id가 존재하지 않습니다");
    }
    Company company = companyMapper.createRequestToEntity(companyCreateRequest);
    companyRepository.save(company);
    return companyMapper.toResponse(company);
  }

  public CompanyResponse updateCompany(CompanyUpdateRequest request, UUID companyId) {
    boolean checkHub = hubClient.checkHubExists(request.getHubId());
    if (!checkHub) {
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
    int pageSize = validatePageSize(pageable.getPageSize());

    // 검증된 pageSize로 새로운 Pageable 객체 생성
    Pageable validatedPageable = PageRequest.of(pageable.getPageNumber(), pageSize);
    Page<CompanyResponse> response = companyRepository.searchCompany(validatedPageable, cond);
    if (response == null) {
      throw new EntityNotFoundException("해당하는 업체가 존재하지 않습니다");
    }
    return response;
  }

  private int validatePageSize(int pageSize) {
    List<Integer> allowedSizes = Arrays.asList(10, 30, 50);
    if (!allowedSizes.contains(pageSize)) {
      return 10; // 기본 값 10으로 설정
    }
    return pageSize;
  }
}
