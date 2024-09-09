package com.sparta.order.domain.model;

import com.sparta.commons.domain.jpa.BaseEntity;
import com.sparta.order.domain.model.State.DeliveryState;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Table(name = "P_DELIVERIES")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLRestriction("is_delete is NULL")
@SQLDelete(sql = "UPDATE p_deliveries SET deleted_at = NOW() where delivery_id = ?")
public class Delivery extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "delivery_id")
  private UUID deliveryId;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "order_id", nullable = false)
  private Order order;

  @Column(nullable = false)
  private UUID departureHubId;

  @Column(nullable = false)
  private UUID arrivalHubId;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private DeliveryState deliveryState = DeliveryState.PENDING;

  @Column(nullable = false)
  private String shippingAddress;

  @Column(name = "shipping_manager_id", nullable = false)
  private UUID shippingManagerId;

  @Column(name = "shipping_manager_slack_id", nullable = false)
  private String shippingManagerSlackId;

  @OneToMany(mappedBy = "delivery", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<DeliveryRoute> routes = new ArrayList<>();

  @Column(nullable = false)
  private LocalDateTime departureDate;

  @Column(nullable = false)
  private LocalDateTime arrivalDate;

  private boolean isDelete = false;

  @Builder
  private Delivery(
      Order order,
      UUID departureHubId,
      UUID arrivalHubId,
      DeliveryState deliveryState,
      String shippingAddress,
      UUID shippingManagerId,
      String shippingManagerSlackId) {
    setOrder(order);
    this.departureHubId = departureHubId;
    this.arrivalHubId = arrivalHubId;
    this.deliveryState = deliveryState;
    this.shippingAddress = shippingAddress;
    this.shippingManagerId = shippingManagerId;
    this.shippingManagerSlackId = shippingManagerSlackId;
  }

  public void setDeliveryDate(LocalDateTime departureDate, LocalDateTime arrivalDate) {
    this.departureDate = departureDate;
    this.arrivalDate = arrivalDate;
  }

  private void setOrder(Order order) {
    this.order = order;
    order.setDelivery(this);
  }
}
