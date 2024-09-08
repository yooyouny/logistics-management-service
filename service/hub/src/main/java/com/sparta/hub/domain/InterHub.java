package com.sparta.hub.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Getter
@Builder
@NoArgsConstructor @AllArgsConstructor
@Table(name = "p_inter_hub")
public class InterHub {

    @Id
    @GeneratedValue
    @Column(name = "inter_hub_id")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "departure_hub_id")
    private Hub departureHub;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "arrival_hub_id")
    private Hub arrivalHub;

    // 소요시간 ( 단위 : 분 )
    @Column(nullable = false)
    private Long elapsedTime;

    @Column(nullable = false)
    private Boolean isDelete = false;
}
