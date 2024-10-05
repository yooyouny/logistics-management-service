package com.sparta.order.infrastructure.repository;

import com.sparta.order.domain.model.Order;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OrderRepository extends JpaRepository<Order, UUID> {
  Optional<Order> findByOrderId(UUID orderID);

  Page<Order> findAllByManagementHubId(UUID managementHubId, Pageable pageable);

  @Query(
      "SELECT o FROM Order o WHERE o.receiverCompanyId = :companyId AND o.orderDate BETWEEN :startDate AND :endDate ORDER BY o.orderDate DESC")
  List<Order> findAllByReceiverCompanyIdAndOrderDateBetween(
      @Param("companyId") UUID companyId,
      @Param("startDate") LocalDateTime startDate,
      @Param("endDate") LocalDateTime endDate);

  @Query(
      "SELECT o FROM Order o WHERE o.supplierCompanyId = :companyId AND o.orderDate BETWEEN :startDate AND :endDate ORDER BY o.orderDate DESC")
  List<Order> findAllBySupplierCompanyIdAndOrderDateBetween(
      @Param("companyId") UUID companyId,
      @Param("startDate") LocalDateTime startDate,
      @Param("endDate") LocalDateTime endDate);
}
