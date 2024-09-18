package com.sparta.hub.application.service;

import com.sparta.commons.domain.exception.BusinessException;
import com.sparta.hub.application.dto.hub.HubCreateRequest;
import com.sparta.hub.application.dto.hub.HubResponse;
import com.sparta.hub.application.dto.hub.HubSearchCond;
import com.sparta.hub.application.dto.hub.HubUpdateRequest;
import com.sparta.hub.application.mapper.HubMapper;
import com.sparta.hub.domain.Hub;
import com.sparta.hub.exception.HubErrorCode;
import com.sparta.hub.infrastructure.config.AuthenticationImpl;
import com.sparta.hub.infrastructure.repository.hub.HubRepository;
import io.micrometer.core.annotation.Timed;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import javax.swing.text.html.Option;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class HubService {

  private final HubRepository hubRepository;
  private final HubMapper hubMapper;

  @Timed("hub")
  @CacheEvict(cacheNames = "hubAllCache", allEntries = true)
  public HubResponse createHub(HubCreateRequest hubCreateRequest) {
    Hub hub = hubMapper.createRequestToEntity(hubCreateRequest);
    hubRepository.save(hub);
    return hubMapper.toResponse(hub);
  }

  @CachePut(cacheNames = "hubCache", key = "#result.hubId")
  @CacheEvict(cacheNames = "hubAllCache", allEntries = true)
  public HubResponse updateHub(HubUpdateRequest requestDto, UUID hubId) {
    Hub hub = hubRepository.findByHubIdAndIsDeleteFalse(hubId)
        .orElseThrow(() -> new BusinessException(HubErrorCode.NOT_FOUND));
    hub.update(requestDto);
    return hubMapper.toResponse(hub);

  }

  @Caching(evict = {@CacheEvict(cacheNames = "hubCache", key = "args[0]"),
      @CacheEvict(cacheNames = "hubAllCache", allEntries = true)})
  public void deleteHub(UUID hubId) {
    AuthenticationImpl authentication = (AuthenticationImpl) SecurityContextHolder.getContext()
        .getAuthentication();
    String username = authentication.getName();
    Hub hub = hubRepository.findById(hubId)
        .orElseThrow(() -> new BusinessException(HubErrorCode.NOT_FOUND));
    if (!hub.getIsDelete()) {
      hub.delete(username);
    } else {
      throw new BusinessException(HubErrorCode.ALREADY_DELETED);
    }
  }

  @Transactional(readOnly = true)
  @Cacheable(cacheNames = "hubCache", key = "args[0]")
  public HubResponse getSingleHub(UUID hubId) {
    Hub hub = hubRepository.findByHubIdAndIsDeleteFalse(hubId)
        .orElseThrow(() -> new BusinessException(HubErrorCode.NOT_FOUND));
    return hubMapper.toResponse(hub);
  }

  @Transactional(readOnly = true)
  @Cacheable(cacheNames = "hubAllCache", key = "#pageable.pageNumber + '-' + #pageable.pageSize + '-' + #cond.name + '-' + #cond.address")
  public Page<HubResponse> getAllHub(Pageable pageable, HubSearchCond cond) {
    int pageSize = validatePageSize(pageable.getPageSize());

    // 검증된 pageSize로 새로운 Pageable 객체 생성
    Pageable validatedPageable = PageRequest.of(pageable.getPageNumber(), pageSize, Sort.unsorted());
    Page<HubResponse> list = hubRepository.searchHub(validatedPageable, cond);
    if (list.isEmpty()) {
      throw new BusinessException(HubErrorCode.NOT_FOUND);
    }
    return list;
  }

  private int validatePageSize(int pageSize) {
    List<Integer> allowedSizes = Arrays.asList(10, 30, 50);
    if (!allowedSizes.contains(pageSize)) {
      return 10; // 기본 값 10으로 설정
    }
    return pageSize;
  }

}
