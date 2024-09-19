package com.sparta.hub.application.service;

import com.sparta.commons.domain.exception.BusinessException;
import com.sparta.hub.application.dto.interhub.InterHubCreateRequest;
import com.sparta.hub.application.dto.interhub.InterHubSearchCond;
import com.sparta.hub.application.dto.interhub.InterHubUpdateRequest;
import com.sparta.hub.application.mapper.InterHubMapper;
import com.sparta.hub.domain.Hub;
import com.sparta.hub.domain.InterHub;
import com.sparta.hub.dto.InterHubResponse;
import com.sparta.hub.exception.HubErrorCode;
import com.sparta.hub.exception.InterHubErrorCode;
import com.sparta.hub.infrastructure.repository.hub.HubRepository;
import com.sparta.hub.infrastructure.repository.interhub.InterHubRepository;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class InterHubService {

  private final InterHubRepository interHubRepository;
  private final HubRepository hubRepository;
  private final InterHubMapper interHubMapper;

  // 하버 사인 공식으로 위,경도를 통한 직선 거리 ( km )
  private static double CalculateDistance(Hub departureHub, Hub arrivalHub) {
    double lat1 = departureHub.getHubLatitude().doubleValue();
    double lon1 = departureHub.getHubLongitude().doubleValue();
    double lat2 = arrivalHub.getHubLatitude().doubleValue();
    double lon2 = arrivalHub.getHubLongitude().doubleValue();

    final int R = 6400;
    double latDistance = Math.toRadians(lat2 - lat1);
    double lonDistance = Math.toRadians(lon2 - lon1);
    double a =
        Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
            + Math.cos(Math.toRadians(lat1))
                * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2)
                * Math.sin(lonDistance / 2);
    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
    double distance = R * c;
    distance = Math.round(distance * 10) / 10.0;
    return distance;
  }

  // TODO 허브 생성/수정/삭제 등에 의한 이동 정보 수정 자동화 (도전?)
  // 이동 간 정보 생성 시 역방향 정보도 함께 생성
  @CacheEvict(cacheNames = "interHubAllCache", allEntries = true)
  public List<InterHubResponse> createRoute(InterHubCreateRequest requestDto) {
    Hub departureHub =
        hubRepository
            .findById(requestDto.getDepartureHubId())
            .orElseThrow(() -> new BusinessException(HubErrorCode.NOT_FOUND));
    Hub arrivalHub =
        hubRepository
            .findById(requestDto.getArrivalHubId())
            .orElseThrow(() -> new BusinessException(HubErrorCode.NOT_FOUND));

    if (departureHub == arrivalHub) {
      throw new IllegalStateException("출발 허브와 도착 허브가 같습니다");
    }
    // 위도 / 경도 값으로 구한 직선 거리 (km)
    double distance = CalculateDistance(departureHub, arrivalHub);
    // 평균 시속 60 km로 갔을때 소요 시간
    long elapsedTime = (long) ((distance / 70.0) * 60);

    if (elapsedTime == 0) {
      throw new BusinessException(InterHubErrorCode.INVALID_ROUTE_SAME_START_END);
    }

    InterHub interHub = new InterHub(departureHub, arrivalHub, distance, elapsedTime);
    InterHub interHubReverse = new InterHub(arrivalHub, departureHub, distance, elapsedTime);

    interHubRepository.save(interHub);
    interHubRepository.save(interHubReverse);
    return interHubMapper.toResponse(interHub, interHubReverse);
  }

  @CachePut(cacheNames = "interHubCache", key = "#result.interHubId")
  @CacheEvict(cacheNames = "interHubAllCache", allEntries = true)
  public InterHubResponse updateRoute(InterHubUpdateRequest requestDto, UUID interHubId) {
    InterHub interHub =
        interHubRepository
            .findById(interHubId)
            .orElseThrow(() -> new BusinessException(InterHubErrorCode.NOT_FOUND));
    Hub departureHub =
        hubRepository
            .findById(requestDto.getDepartureHubId())
            .orElseThrow(() -> new BusinessException(HubErrorCode.NOT_FOUND));
    Hub arrivalHub =
        hubRepository
            .findById(requestDto.getArrivalHubId())
            .orElseThrow(() -> new BusinessException(HubErrorCode.NOT_FOUND));
    double distance = CalculateDistance(departureHub, arrivalHub);
    interHub.update(departureHub, arrivalHub, requestDto.getElapsedTime(), distance);
    return interHubMapper.toResponse(interHub);
  }

  @Caching(
      evict = {
        @CacheEvict(cacheNames = "interHubCache", key = "args[0]"),
        @CacheEvict(cacheNames = "interHubAllCache", allEntries = true)
      })
  public void delete(UUID interHubId, String email) {
    InterHub interHub =
        interHubRepository
            .findById(interHubId)
            .orElseThrow(() -> new BusinessException(InterHubErrorCode.NOT_FOUND));
    interHub.delete(email);
  }

  @Transactional(readOnly = true)
  @Cacheable(cacheNames = "interHubCache", key = "args[0]")
  public InterHubResponse getOneHubRoute(UUID interHubId) {
    InterHub interHub =
        interHubRepository
            .findById(interHubId)
            .orElseThrow(() -> new BusinessException(InterHubErrorCode.NOT_FOUND));
    return interHubMapper.toResponse(interHub);
  }

  @Transactional(readOnly = true)
  @Cacheable(
      cacheNames = "interHubAllCache",
      key =
          "#pageable.pageNumber + '-' + #pageable.pageSize + '-' + #cond.departureHubId + '-' + #cond.arrivalHubId + '-' + #cond.departureHubName + '-' + #cond.arrivalHubName")
  public Page<InterHubResponse> getAllHubRoute(InterHubSearchCond cond, Pageable pageable) {
    int pageSize = validatePageSize(pageable.getPageSize());
    Page<InterHubResponse> list = interHubRepository.searchHub(pageable, cond);
    if (list.isEmpty()) {
      throw new BusinessException(InterHubErrorCode.NOT_FOUND);
    }
    return list;
  }

  public InterHubResponse findInterHubByDpHubAndAvHub(UUID departureHubId, UUID arrivalHubId) {
    InterHub interHub =
        interHubRepository
            .findByDpHubAndAvHub(departureHubId, arrivalHubId)
            .orElseThrow(() -> new BusinessException(InterHubErrorCode.NOT_FOUND));
    return interHubMapper.toResponse(interHub);
  }

  private int validatePageSize(int pageSize) {
    List<Integer> allowedSizes = Arrays.asList(10, 30, 50);
    if (!allowedSizes.contains(pageSize)) {
      return 10; // 기본 값 10으로 설정
    }
    return pageSize;
  }
}
