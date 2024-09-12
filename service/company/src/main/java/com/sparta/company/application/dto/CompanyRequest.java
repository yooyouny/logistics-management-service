package com.sparta.company.application.dto;

import com.sparta.company.domain.CompanyType;
import java.util.UUID;
import lombok.Data;

@Data
public class CompanyRequest {
  private Long userId;
  private UUID hubId;
  private String companyName;
  private CompanyType companyType;
  private String companyAddress;
}
