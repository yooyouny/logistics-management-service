package com.sparta.hub.infrastructure.feign;

import java.util.UUID;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "company")
public interface CompanyClient {
  @GetMapping("/internal/companies/{companyId}")
  UUID findHubByCompanyId(@PathVariable("companyId") UUID companyId);
}
