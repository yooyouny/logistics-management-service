package com.sparta.company.domain;

public enum CompanyType {
  SUPPLIER("제공 업체"), RECEIVER("주문 업체");

  final String description;

  CompanyType(String description) {
    this.description = description;
  }
}
