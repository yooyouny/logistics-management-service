package com.sparta.company.presentation.controller;

import com.sparta.company.application.service.CompanyInternalService;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/internal/companies")
public class CompanyInternalController {

  private final CompanyInternalService internalService;

  @GetMapping("/{companyId}")
  Optional<UUID> getHubIdByCompanyId(@PathVariable("companyId") UUID companyId){
    return internalService.getHubIdByCompanyId(companyId);
  }
}
