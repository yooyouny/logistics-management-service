package com.sparta.company.domain;

import com.sparta.commons.domain.jpa.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "p_companies")
@SQLRestriction("is_delete is false")
public class Company extends BaseEntity {

  @Id
  @GeneratedValue
  @Column(name = "company_id")
  private UUID companyId;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private CompanyType companyType;

  @Column(length = 100, nullable = false)
  private String companyName;

  @Column(nullable = false)
  private String companyAddress;
  private boolean isDelete = false;
  private UUID HubId;
  private Long userId;

}
