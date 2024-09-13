package com.sparta.company.application.dto.company;

import com.sparta.company.domain.CompanyType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import lombok.Data;

@Data
public class CompanyUpdateRequest {
  @NotNull
  private Long userId;
  @NotNull
  private UUID hubId;
  @NotBlank
  private String companyName;
  @NotBlank
  private String companyAddress;
  @NotNull
  private CompanyType companyType;
}
