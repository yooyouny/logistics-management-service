package com.sparta.company.infrastructure.repository.product;

import com.sparta.company.domain.Product;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, UUID>, ProductRepositoryCustom {

}
