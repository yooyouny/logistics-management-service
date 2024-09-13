package com.sparta.company.application.dto;

import com.sparta.company.domain.CompanyType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import lombok.Data;

@Data
public class CompanyCreateRequest {

  @NotNull
  private Long userId;
  @NotNull
  private UUID hubId;
  @NotBlank
  private String companyName;
  @NotNull
  private CompanyType companyType;
  @NotBlank
  private String companyAddress;
}
