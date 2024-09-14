package com.sparta.hub.domain;

import com.sparta.commons.domain.jpa.BaseEntity;
import com.sparta.hub.application.dto.hub.HubUpdateRequest;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Getter
@Table(name = "p_hubs")
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLRestriction("is_delete is false")
public class Hub extends BaseEntity {

  @Id
  @GeneratedValue
  @Column(name = "hub_id")
  private UUID hubId;

  @Column(length = 100, nullable = false)
  private String hubName;
  @Column(nullable = false)
  private String hubAddress;
  @Column(precision = 7, scale = 4, nullable = false)
  private BigDecimal hubLatitude;
  @Column(precision = 7, scale = 4, nullable = false)
  private BigDecimal hubLongitude;
  @Column(nullable = false)
  private Long userId;
  private Boolean isDelete = false;


  public void update(HubUpdateRequest requestDto) {
    if (requestDto.getHubName() != null) {
      hubName = requestDto.getHubName();
    }

    if (requestDto.getHubAddress() != null) {
      hubAddress = requestDto.getHubAddress();
    }

    if (requestDto.getHubLatitude() != null) {
      hubLatitude = requestDto.getHubLatitude();
    }

    if (requestDto.getHubLongitude() != null) {
      hubLongitude = requestDto.getHubLongitude();
    }

  }

  public void delete(String username) {
    isDelete = true;
    deletedAt = LocalDateTime.now();
    // TODO deletedBy 수동 설정
    deletedBy = username;
  }
}
