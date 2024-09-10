package com.sparta.hub.application.service;

import com.sparta.hub.application.dto.interhub.InterHubCreateRequest;
import com.sparta.hub.application.dto.interhub.InterHubResponse;
import com.sparta.hub.application.dto.interhub.InterHubSearchCond;
import com.sparta.hub.application.dto.interhub.InterHubUpdateRequest;
import com.sparta.hub.application.mapper.InterHubMapper;
import com.sparta.hub.domain.Hub;
import com.sparta.hub.domain.InterHub;
import com.sparta.hub.infrastructure.repository.hub.HubRepository;
import com.sparta.hub.infrastructure.repository.interhub.InterHubRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class InterHubService {

    private final InterHubRepository interHubRepository;
    private final HubRepository hubRepository;
    private final InterHubMapper interHubMapper;



    //TODO 허브 생성/수정/삭제 등에 의한 이동 정보 수정 자동화 (도전?)
    @CacheEvict(cacheNames = "interHubAllCache", allEntries = true)
    public List<InterHubResponse> createRoute(InterHubCreateRequest requestDto) {
        Hub departureHub = hubRepository.findById(requestDto.getDepartureHubId()).orElseThrow(
                () -> new EntityNotFoundException("해당허브가 존재하지 않습니다"));
        Hub arrivalHub = hubRepository.findById(requestDto.getArrivalHubId()).orElseThrow(
                () -> new EntityNotFoundException("해당허브가 존재하지 않습니다"));

        if (departureHub == arrivalHub) {
            throw new IllegalStateException("출발 허브와 도착 허브가 같습니다");
        }

        long elapsedTime = CalculateElapsedTime(departureHub, arrivalHub);

        if (elapsedTime == 0) {
            throw new IllegalStateException("출발 허브와 도착 허브가 같습니다");
        }

        InterHub interHub = InterHub.builder()
                .departureHub(departureHub)
                .arrivalHub(arrivalHub)
                .elapsedTime(elapsedTime)
                .isDelete(false)
                .build();

        InterHub interHubReverse = InterHub.builder()
                .departureHub(arrivalHub)
                .arrivalHub(departureHub)
                .elapsedTime(elapsedTime)
                .isDelete(false)
                .build();

        interHubRepository.save(interHub);
        interHubRepository.save(interHubReverse);
        return interHubMapper.toResponse(interHub, interHubReverse);
    }

    // 하버 사인 공식으로 위,경도를 통한 직선 거리를 구하고
    // 평균 시속 60km로 갔을 때의 소요 시간 ( 단위 : 분 )
    private static long CalculateElapsedTime(Hub departureHub, Hub arrivalHub) {
        double lat1 = departureHub.getHubLatitude().doubleValue();
        double lon1 = departureHub.getHubLongitude().doubleValue();
        double lat2 = arrivalHub.getHubLatitude().doubleValue();
        double lon2 = arrivalHub.getHubLongitude().doubleValue();

        final int R = 6400; // 지구 반지름 (단위: km)
        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c; // 두 허브 간 거리 (단위: km)
        return (long) ((distance / 60.0) * 60);
    }

    @CachePut(cacheNames = "interHubCache", key = "#result.interHubId")
    @CacheEvict(cacheNames = "interHubAllCache", allEntries = true)
    public InterHubResponse updateRoute(InterHubUpdateRequest requestDto, UUID interHubId) {
        InterHub interHub = interHubRepository.findById(interHubId).orElseThrow(() -> new EntityNotFoundException("해당 허브 간 이동 정보가 없습니다"));
        Hub departureHub = hubRepository.findById(requestDto.getDepartureHubId()).orElseThrow(() -> new EntityNotFoundException("출발 허브 정보가 없습니다"));
        Hub arrivalHub = hubRepository.findById(requestDto.getArrivalHubId()).orElseThrow(() -> new EntityNotFoundException("도착 허브 정보가 없습니다"));
        interHub.update(departureHub, arrivalHub, requestDto.getElapsedTime());
        return interHubMapper.toResponse(interHub);
    }

    @Caching(evict = {
            @CacheEvict(cacheNames = "interHubCache", key = "args[0]"),
            @CacheEvict(cacheNames = "interHubAllCache", allEntries = true)})
    public void delete(UUID interHubId, String email) {
        InterHub interHub = interHubRepository.findById(interHubId).orElseThrow(() -> new EntityNotFoundException("해당 허브 간 이동 정보가 없습니다"));
        interHub.delete(email);
    }

    @Transactional(readOnly = true)
    @Cacheable(cacheNames = "interHubCache", key = "args[0]")
    public InterHubResponse getOneHubRoute(UUID interHubId) {
        InterHub interHub = interHubRepository.findById(interHubId).orElseThrow(() -> new EntityNotFoundException("해당 허브 간 이동 정보가 없습니다"));
        return interHubMapper.toResponse(interHub);
    }

    @Transactional(readOnly = true)
    @Cacheable(cacheNames = "interHubAllCache", key = "#pageable.pageNumber + '-' + #pageable.pageSize + '-' + #cond.departureHubId + '-' + #cond.arrivalHubId + '-' + #cond.departureHubName + '-' + #cond.arrivalHubName")
    public  Page<InterHubResponse> getAllHubRoute(InterHubSearchCond cond, Pageable pageable) {
        Page<InterHubResponse> list = interHubRepository.searchHub(pageable, cond);
        if (list.isEmpty()) {
            throw new EntityNotFoundException("허브 간 이동 정보가 존재하지 않습니다");
        }
        return list;
    }
}
