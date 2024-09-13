package com.sparta.company.presentation.controller;

import com.sparta.commons.domain.response.ResponseBody;
import com.sparta.commons.domain.response.SuccessResponseBody;
import com.sparta.company.application.dto.CompanyCreateRequest;
import com.sparta.company.application.dto.CompanyResponse;
import com.sparta.company.application.dto.CompanySearchCond;
import com.sparta.company.application.dto.CompanyUpdateRequest;
import com.sparta.company.application.service.CompanyService;
import feign.Response;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/companies")
public class CompanyController {

  private final CompanyService companyService;

  @PostMapping
  public ResponseBody<CompanyResponse> createCompany(
      @Valid @RequestBody CompanyCreateRequest companyCreateRequest) {
    CompanyResponse response = companyService.createCompany(companyCreateRequest);
    return new SuccessResponseBody<>(response);
  }

  @PutMapping("/{companyId}")
  public ResponseBody<CompanyResponse> updateCompany(
      @Valid @RequestBody CompanyUpdateRequest request, @PathVariable UUID companyId) {
    CompanyResponse companyResponse = companyService.updateCompany(request, companyId);
    return new SuccessResponseBody<>(companyResponse);
  }

  @DeleteMapping("/{companyId}")
  public ResponseBody<UUID> deleteCompany(@PathVariable UUID companyId) {
    companyService.deleteCompany(companyId);
    return new SuccessResponseBody<>(companyId);
  }

  @GetMapping("/{companyId}")
  public ResponseBody<CompanyResponse> getCompany(@PathVariable UUID companyId) {
    CompanyResponse response = companyService.findOneCompany(companyId);
    return new SuccessResponseBody<>(response);
  }

  @GetMapping
  public ResponseBody<Page<CompanyResponse>> getAllCompanies(Pageable pageable, CompanySearchCond cond) {
    Page<CompanyResponse> response = companyService.findAllCompanies(pageable, cond);
    return new SuccessResponseBody<>(response);
  }

}
