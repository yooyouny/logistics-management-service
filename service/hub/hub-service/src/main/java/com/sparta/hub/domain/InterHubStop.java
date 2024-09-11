package com.sparta.hub.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(name = "p_inter_hub_stops")
public class InterHubStop {

  @Id
  @GeneratedValue
  @Column(name = "inter_hub_stop_id")
  private Long InterHubStopId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "inter_hub_id")
  private InterHub interHub;

  private Integer sequence;

  @JsonIgnore
  @Column(name = "arrival_hub_id", nullable = false)
  private UUID arrivalHubId;

  @JsonIgnore
  @Column(name = "departure_hub_id", nullable = false)
  private UUID departureHubId;

  @Column(name = "elapsed_time", nullable = false)
  private Long elapsedTime;  // 소요시간

  @Column(nullable = false)
  private double distance;

  public void addStop(InterHub interHub) {
    this.interHub = interHub;
    this.interHub.getInterHubStops().add(this);
  }

  public InterHubStop(InterHub interHub, Integer sequence, UUID arrivalHubId, UUID departureHubId,
      Long elapsedTime, double distance) {
    this.interHub = interHub;
    this.sequence = sequence;
    this.arrivalHubId = arrivalHubId;
    this.departureHubId = departureHubId;
    this.elapsedTime = elapsedTime;
    this.distance = distance;
  }
}
