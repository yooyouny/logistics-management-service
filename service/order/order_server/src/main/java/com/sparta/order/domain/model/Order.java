package com.sparta.order.domain.model;

import com.sparta.commons.domain.jpa.BaseEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Table(name = "P_ORDERS")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLRestriction("is_delete is false")
@SQLDelete(sql = "UPDATE p_orders SET deleted_at = NOW() where order_id = ?")
@Getter
public class Order extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "order_id")
  private UUID orderId;

  @Column(nullable = false, name = "supplier_company_id")
  private UUID supplierCompanyId;

  @Column(nullable = false, name = "receiver_company_id")
  private UUID receiverCompanyId;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private OrderState orderState = OrderState.CREATED;

  private LocalDateTime orderDate;

  @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<OrderDetail> orderDetails = new ArrayList<>();

  @Embedded private Money totalAmount = Money.ZERO;

  private int totalQuantity = 0;

  @Column(nullable = false)
  private UUID deliveryId;

  private boolean isDelete = false;

  @Builder
  private Order(UUID supplierCompanyId, UUID receiverCompanyId, LocalDateTime orderDate) {
    this.supplierCompanyId = supplierCompanyId;
    this.receiverCompanyId = receiverCompanyId;
    this.orderDate = orderDate;
  }

  public void setOrderDetails(List<OrderDetail> orderDetailList) {
    this.orderDetails = new ArrayList<>(orderDetailList);
    this.totalAmount = calculateTotalAmount(orderDetailList);
    this.totalQuantity = calculateTotalQuantity(orderDetailList);
  }

  public void updateOrderState(OrderState state) {
    this.orderState = state;
  }

  public void setDeliveryId(UUID deliveryId) {
    this.deliveryId = deliveryId;
  }

  private Money calculateTotalAmount(List<OrderDetail> orderDetailList) {
    return Money.won(
        orderDetailList.stream()
            .mapToInt(orderDetail -> orderDetail.getUnitPrice() * orderDetail.getQuantity())
            .sum());
  }

  private int calculateTotalQuantity(List<OrderDetail> orderDetailList) {
    return orderDetailList.stream().mapToInt(orderDetail -> orderDetail.getQuantity()).sum();
  }
}
