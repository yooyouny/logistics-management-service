package com.sparta.hub.domain;

import com.sparta.commons.domain.exception.BusinessException;
import com.sparta.commons.domain.jpa.BaseEntity;
import com.sparta.hub.exception.AlreadyDeletedException;
import com.sparta.hub.exception.InterHubErrorCode;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "p_inter_hubs")
@SQLRestriction("is_delete = false")
public class InterHub extends BaseEntity {

  @Id
  @GeneratedValue
  @Column(name = "inter_hub_id")
  private UUID interHubId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "departure_hub_id")
  private Hub departureHub;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "arrival_hub_id")
  private Hub arrivalHub;

  @Column(nullable = false)
  private Double distance;
  // 소요시간 ( 단위 : 분 )
  @Column(nullable = false)
  private Long elapsedTime;

  @Column(nullable = false)
  private Boolean isDelete = false;

  @OneToMany(mappedBy = "interHub")
  private List<InterHubStop> interHubStops = new ArrayList<>();

  public void update(Hub departureHub, Hub arrivalHub, Long elapsedTime, Double distance) {
    this.departureHub = departureHub;
    this.arrivalHub = arrivalHub;
    this.distance = distance;
    this.elapsedTime = elapsedTime;
  }

  //TODO deletedBy 임의로 헤더에서 emial을 넣는걸로 설정, 이후 수정
  public void delete(String email) {
    if (isDelete) {
      throw new BusinessException(InterHubErrorCode.ALREADY_DELETED);
    }
    isDelete = true;
    deletedAt = LocalDateTime.now();
    deletedBy = email;
  }

  public InterHub(Hub departureHub, Hub arrivalHub, Double distance, Long elapsedTime) {
    this.departureHub = departureHub;
    this.arrivalHub = arrivalHub;
    this.distance = distance;
    this.elapsedTime = elapsedTime;
  }
}
