package com.sparta.delivery.infrastructure.client;

import java.util.Optional;
import java.util.UUID;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "company")
public interface CompanyClient {
  @GetMapping("/internal/companies/{companyId}")
  Optional<UUID> getHubIdByCompanyId(@PathVariable("companyId") UUID companyId);
}
