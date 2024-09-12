package com.sparta.company.presentation.controller;

import com.sparta.commons.domain.response.ResponseBody;
import com.sparta.commons.domain.response.SuccessResponseBody;
import com.sparta.company.application.dto.CompanyRequest;
import com.sparta.company.application.dto.CompanyResponse;
import com.sparta.company.application.service.CompanyService;
import com.sparta.company.infrastructure.client.HubClient;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/companies")
public class CompanyController {

  private final CompanyService companyService;

  @PostMapping
  public ResponseBody<CompanyResponse> createCompany(@Valid @RequestBody CompanyRequest companyRequest) {
    CompanyResponse response = companyService.createCompany(companyRequest);
    return new SuccessResponseBody<>(response);
  }


}
