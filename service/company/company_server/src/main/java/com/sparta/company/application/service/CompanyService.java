package com.sparta.company.application.service;

import com.sparta.commons.domain.exception.BusinessException;
import com.sparta.company.application.dto.company.CompanyCreateRequest;
import com.sparta.company.application.dto.company.CompanyResponse;
import com.sparta.company.application.dto.company.CompanySearchCond;
import com.sparta.company.application.dto.company.CompanyUpdateRequest;
import com.sparta.company.application.mapper.CompanyMapper;
import com.sparta.company.domain.Company;
import com.sparta.company.domain.strategy.company.update.CompanyUpdateStrategy;
import com.sparta.company.domain.strategy.company.update.CompanyUpdateStrategyFactory;
import com.sparta.company.exception.CompanyErrorCode;
import com.sparta.company.exception.HubErrorCode;
import com.sparta.company.infrastructure.client.HubClient;
import com.sparta.company.infrastructure.client.UserClient;
import com.sparta.company.infrastructure.configuration.AuthenticationImpl;
import com.sparta.company.infrastructure.repository.company.CompanyRepository;
import com.sparta.user.dto.user_dto.UserDto;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Slf4j
public class CompanyService {

  private final CompanyRepository companyRepository;
  private final HubClient hubClient;
  private final CompanyMapper companyMapper = new CompanyMapper();
  private final UserClient userClient;
  private final CompanyUpdateStrategyFactory strategyFactory;

  public CompanyService(CompanyRepository companyRepository, HubClient hubClient,
      UserClient userClient) {
    this.companyRepository = companyRepository;
    this.hubClient = hubClient;
    this.userClient = userClient;
    this.strategyFactory = new CompanyUpdateStrategyFactory(hubClient, userClient);
  }

  @PreAuthorize("isAuthenticated() and hasAnyRole('ROLE_MASTER', 'ROLE_HUB_COMPANY', 'ROLE_HUB_MANAGER')")
  public CompanyResponse createCompany(CompanyCreateRequest companyCreateRequest) {
    checkHubExists(companyCreateRequest.getHubId());
    Company company = companyMapper.createRequestToEntity(companyCreateRequest);
    companyRepository.save(company);
    return companyMapper.toResponse(company);
  }

  @PreAuthorize("isAuthenticated() and hasAnyRole('ROLE_MASTER', 'ROLE_HUB_COMPANY', 'ROLE_HUB_MANAGER')")
  public CompanyResponse updateCompany(CompanyUpdateRequest request, UUID companyId, AuthenticationImpl authentication) {
    checkHubExists(request.getHubId());

    String username = authentication.getName();
    String role = authentication.role();
    Company company = getCompany(companyId);
    Long userId = authentication.userId();

    CompanyUpdateStrategy strategy = strategyFactory.createStrategy(role);
    strategy.update(request, company, username,userId);

    return companyMapper.toResponse(company);
  }

  private Long getUserIdFromUsername(String username) {
    Optional<UserDto> userDto = userClient.getUserDto(username);
    UserDto user = userDto.orElseThrow(
        () -> new BusinessException(CompanyErrorCode.USER_NOT_FOUND));
    Long userId = user.userId();
    return userId;
  }

  @PreAuthorize("isAuthenticated() and hasAnyRole('ROLE_MASTER', 'ROLE_HUB_COMPANY', 'ROLE_HUB_MANAGER')")
  public void deleteCompany(UUID companyId) {
    AuthenticationImpl authentication = (AuthenticationImpl) SecurityContextHolder.getContext().getAuthentication();
    String username = authentication.getName();
    String role = authentication.role();

    Company company = getCompany(companyId);
    if(role.equals("ROLE_HUB_COMPANY")) {
      Long userId = getUserIdFromUsername(username);
      if(!userId.equals(company.getUserId())) {
        company.delete(username);
      }else{
        throw new BusinessException(CompanyErrorCode.ACCESS_DENIED);
      }
    }
    company.delete(username);
  }

  private Company getCompany(UUID companyId) {
    Company company = companyRepository.findById(companyId).orElseThrow(
        () -> new BusinessException(CompanyErrorCode.NOT_FOUND)
    );
    return company;
  }

  @Transactional(readOnly = true)
  public CompanyResponse findOneCompany(UUID companyId) {
    Company company = getCompany(companyId);
    return companyMapper.toResponse(company);
  }

  @Transactional(readOnly = true)
  public Page<CompanyResponse> findAllCompanies(Pageable pageable, CompanySearchCond cond) {
    int pageSize = validatePageSize(pageable.getPageSize());

    // 검증된 pageSize로 새로운 Pageable 객체 생성
    Pageable validatedPageable = PageRequest.of(pageable.getPageNumber(), pageSize);
    Page<CompanyResponse> response = companyRepository.searchCompany(validatedPageable, cond);
    if (response == null) {
      throw new BusinessException(CompanyErrorCode.NOT_FOUND);
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

  private void checkHubExists(UUID hubId) {
    boolean checkHub = hubClient.checkHubExists(hubId);
    if (!checkHub) {
      throw new BusinessException(HubErrorCode.NOT_FOUND);
    }
  }
}
