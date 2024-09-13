package com.sparta.company.domain;

import jakarta.persistence.Embeddable;

public enum CompanyType {
  SUPPLIER("제공 업체"), RECiEVER("주문 업체");

  final String description;

  CompanyType(String description) {
    this.description = description;
  }
}
