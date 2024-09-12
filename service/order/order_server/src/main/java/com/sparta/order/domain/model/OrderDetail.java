package com.sparta.order.domain.model;

import com.sparta.commons.domain.jpa.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "P_ORDER_DETAILS")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class OrderDetail extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID orderDetailId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "order_id", nullable = false)
  private Order order;

  @Column(nullable = false)
  private UUID productId;

  @Column(nullable = false)
  private String productName;

  @Getter private int quantity = 0;

  @Getter private int unitPrice = 0;

  @Builder
  private OrderDetail(
      Order order, UUID productId, String productName, int quantity, int unitPrice) {
    this.order = order;
    this.productId = productId;
    this.productName = productName;
    this.quantity = quantity;
    this.unitPrice = unitPrice;
  }
}
