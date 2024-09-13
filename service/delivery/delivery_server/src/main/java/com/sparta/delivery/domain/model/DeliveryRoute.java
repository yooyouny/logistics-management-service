package com.sparta.delivery.domain.model;

import com.sparta.commons.domain.jpa.BaseEntity;
import com.sparta.delivery.domain.model.State.RouteState;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.Duration;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "P_DELIVERY_ROUTES")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class DeliveryRoute extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "delivery_route_id")
  private UUID deliveryRouteId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "delivery_id", nullable = false)
  private Delivery delivery;

  @Column(nullable = false)
  private int sequence;

  @Column(nullable = false)
  private UUID departureHubId;

  @Column(nullable = false)
  private UUID arrivalHubId;

  private double estimatedDistance;
  private long estimatedElapsedTime;

  private Integer realDistance;

  private Duration realElapsedTime;

  private RouteState routeState = RouteState.PENDING;

  @Builder
  private DeliveryRoute(
      Delivery delivery,
      int sequence,
      UUID departureHubId,
      UUID arrivalHubId,
      double estimatedDistance,
      long estimatedElapsedTime,
      Integer realDistance,
      Duration realElapsedTime) {
    this.delivery = delivery;
    this.sequence = sequence;
    this.departureHubId = departureHubId;
    this.arrivalHubId = arrivalHubId;
    this.estimatedDistance = estimatedDistance;
    this.estimatedElapsedTime = estimatedElapsedTime;
    this.realDistance = realDistance;
    this.realElapsedTime = realElapsedTime;
  }

  public void updateRouteState(RouteState state){
    this.routeState = state;
  }
}
