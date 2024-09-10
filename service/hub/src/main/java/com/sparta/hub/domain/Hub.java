package com.sparta.hub.domain;

import com.sparta.commons.domain.jpa.BaseEntity;
import com.sparta.hub.application.dto.hub.HubUpdateRequest;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLRestriction;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

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

    public void delete(String email) {
        isDelete = true;
        deletedAt = LocalDateTime.now();
        // TODO deletedBy 수동 설정
        deletedBy = email;
    }
}
