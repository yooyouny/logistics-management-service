package com.sparta.company.infrastructure.metrics;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tags;
import org.springframework.stereotype.Component;

@Component
public class StockMetrics {

  private final MeterRegistry registry;

  public StockMetrics(MeterRegistry meterRegistry) {
    this.registry = meterRegistry;
  }

  public void registerStockQuantity(String productName, int stockQuantity) {
    registry.gauge("product_stock_quantity", Tags.of("product", productName), stockQuantity);
  }
}

