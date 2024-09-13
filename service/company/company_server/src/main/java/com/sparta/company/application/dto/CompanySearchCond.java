package com.sparta.company.application.dto;

import com.sparta.company.domain.CompanyType;
import java.util.UUID;
import lombok.Data;
import lombok.Getter;

@Data
public class CompanySearchCond {

  private String companyName;
  private UUID hubId;
  private CompanyType companyType;
}
