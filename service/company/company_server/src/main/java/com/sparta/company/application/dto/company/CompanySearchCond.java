package com.sparta.company.application.dto.company;

import com.sparta.company.domain.CompanyType;
import java.util.UUID;
import lombok.Data;

@Data
public class CompanySearchCond {

  private String companyName;
  private UUID hubId;
  private CompanyType companyType;
}
