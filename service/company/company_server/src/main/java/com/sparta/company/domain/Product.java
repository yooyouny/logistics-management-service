package com.sparta.company.domain;

import com.sparta.commons.domain.exception.BusinessException;
import com.sparta.commons.domain.jpa.BaseEntity;
import com.sparta.company.application.dto.product.ProductUpdateRequest;
import com.sparta.company.exception.ProductErrorCode;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "p_products")
@SQLRestriction("is_delete is false")
public class Product extends BaseEntity {

  @Id
  @GeneratedValue
  @Column(name = "product_id")
  private UUID productId;
  @Column(nullable = false)
  private String productName;
  @Column(nullable = false)
  private Integer productPrice;
  @Column(nullable = false)
  private String productDescription;
  @Column(nullable = false)
  private Integer stockQuantity;
  private boolean isDelete = false;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "company_id")
  private Company company;
  private UUID hubId;

  private void addProduct(Company company) {
    this.company = company;
    if (company != null) {
      company.getProducts().add(this);
    }
  }

  public void deductQuantity(int quantity) {
    if (stockQuantity < quantity) {
      throw new BusinessException(ProductErrorCode.ORDER_QUANTITY_EXCEEDS_STOCK);
    }
    if (stockQuantity == 0) {
      throw new BusinessException(ProductErrorCode.OUT_OF_STOCK);
    }
    this.stockQuantity -= quantity;
  }

  public void update(ProductUpdateRequest request, Company company) {
    productName = request.getProductName();
    productDescription = request.getProductDescription();
    productPrice = request.getProductPrice();
    stockQuantity = request.getStockQuantity();
    hubId = request.getHubId();
    this.company = company;
    super.updatedAt = LocalDateTime.now();
  }

  public void delete(String username) {
    isDelete = true;
    super.deletedAt = LocalDateTime.now();
    deletedBy = username;
  }
}
