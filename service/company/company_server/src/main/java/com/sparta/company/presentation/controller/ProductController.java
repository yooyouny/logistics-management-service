package com.sparta.company.presentation.controller;

import com.sparta.commons.domain.response.ResponseBody;
import com.sparta.commons.domain.response.SuccessResponseBody;
import com.sparta.company.application.dto.product.ProductCreateRequest;
import com.sparta.company.application.dto.product.ProductResponse;
import com.sparta.company.application.dto.product.ProductSearchCond;
import com.sparta.company.application.dto.product.ProductUpdateRequest;
import com.sparta.company.application.service.ProductService;
import jakarta.validation.Valid;
import jakarta.ws.rs.Path;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
@Slf4j
public class ProductController {

  private final ProductService productService;

  @PostMapping
  public ResponseBody<ProductResponse> createProduct(
      @Valid @RequestBody ProductCreateRequest request) {
    log.info("???");
    ProductResponse response = productService.createProduct(request);
    return new SuccessResponseBody<>(response);
  }

  @GetMapping("/{productId}")
  public ResponseBody<ProductResponse> getProduct(@PathVariable UUID productId) {
    ProductResponse response = productService.getOneProduct(productId);
    return new SuccessResponseBody<>(response);
  }

  @GetMapping
  public ResponseBody<Page<ProductResponse>> searchProducts(Pageable pageable,
      ProductSearchCond cond) {
    Page<ProductResponse> response = productService.searchProduct(pageable, cond);
    return new SuccessResponseBody<>(response);
  }

  @PutMapping("/{productId}")
  public ResponseBody<ProductResponse> updateProduct(@RequestBody ProductUpdateRequest request,@PathVariable UUID productId) {
    ProductResponse response = productService.updateProduct(request,productId);
    return new SuccessResponseBody<>(response);
  }

  @DeleteMapping("/{productId}")
  public ResponseBody<UUID> deleteProduct(@PathVariable UUID productId) {
    productService.deleteProduct(productId);
    return new SuccessResponseBody<>(productId);
  }

}
