package com.sparta.user.domain.model.vo;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum UserRole {
  ROLE_MASTER("마스터관리자"),
  ROLE_HUB_MANAGER("허브관리자"),
  ROLE_HUB_SHIPPING_MANAGER("허브배송담당자"),
  ROLE_HUB_COMPANY("허브업체");

  private final String role;

  @JsonCreator
  public static UserRole fromString(String role) {
    for (UserRole userRole : UserRole.values()) {
      if (userRole.role.equals(role)) {
        return userRole;
      }
    }
    return null;
  }

  @JsonValue
  public String getRole() {
    return role;
  }
}
