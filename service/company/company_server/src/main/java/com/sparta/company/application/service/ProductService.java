package com.sparta.company.application.service;

import com.sparta.commons.domain.exception.BusinessException;
import com.sparta.company.application.dto.product.ProductCreateRequest;
import com.sparta.company.application.dto.product.ProductResponse;
import com.sparta.company.application.dto.product.ProductSearchCond;
import com.sparta.company.application.dto.product.ProductUpdateRequest;
import com.sparta.company.application.mapper.ProductMapper;
import com.sparta.company.domain.Company;
import com.sparta.company.domain.Product;
import com.sparta.company.exception.CompanyErrorCode;
import com.sparta.company.exception.HubErrorCode;
import com.sparta.company.exception.ProductErrorCode;
import com.sparta.company.infrastructure.client.HubClient;
import com.sparta.company.infrastructure.repository.company.CompanyRepository;
import com.sparta.company.infrastructure.repository.product.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductService {

  private final ProductRepository productRepository;
  private final CompanyRepository companyRepository;
  private final HubClient hubClient;
  private final ProductMapper productMapper = new ProductMapper();

  public ProductResponse createProduct(ProductCreateRequest request) {
    Company company = getCompany(request.getCompanyId());

    boolean checkHub = hubClient.checkHubExists(request.getHubId());
    if (!checkHub) {
      throw new BusinessException(HubErrorCode.NOT_FOUND);
    }

    Product product = productMapper.createDtoToEntity(request, company);
    productRepository.save(product);
    return productMapper.toResponse(product);
  }

  @Transactional(readOnly = true)
  public ProductResponse getOneProduct(UUID productId) {
    Product product = getProduct(productId);
    return productMapper.toResponse(product);
  }


  @Transactional(readOnly = true)
  public Page<ProductResponse> searchProduct(Pageable pageable, ProductSearchCond cond) {
    int pageSize = validatePageSize(pageable.getPageSize());

    // 검증된 pageSize로 새로운 Pageable 객체 생성
    Pageable validatedPageable = PageRequest.of(pageable.getPageNumber(), pageSize);
    Page<ProductResponse> response = productRepository.searchProduct(validatedPageable, cond);
    return response;
  }

  public ProductResponse updateProduct(ProductUpdateRequest request,UUID productId) {
    Product product = getProduct(productId);
    Company company = getCompany(request.getCompanyId());
    product.update(request, company);
    return productMapper.toResponse(product);
  }

  public void deleteProduct(UUID productId) {
    Product product = getProduct(productId);
    product.delete();
  }

  private int validatePageSize(int pageSize) {
    List<Integer> allowedSizes = Arrays.asList(10, 30, 50);
    if (!allowedSizes.contains(pageSize)) {
      return 10; // 기본 값 10으로 설정
    }
    return pageSize;
  }

  private Company getCompany(UUID companyId) {
    return companyRepository.findById(companyId)
        .orElseThrow(() -> new BusinessException(CompanyErrorCode.NOT_FOUND));
  }

  private Product getProduct(UUID productId) {
    return productRepository.findById(productId)
        .orElseThrow(() -> new BusinessException(ProductErrorCode.NOT_FOUND));
  }


}
