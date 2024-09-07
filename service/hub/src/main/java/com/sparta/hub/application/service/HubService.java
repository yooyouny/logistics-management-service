package com.sparta.hub.application.service;

import com.sparta.hub.application.dto.HubCreateRequest;
import com.sparta.hub.application.dto.HubResponse;
import com.sparta.hub.application.dto.HubSearchCond;
import com.sparta.hub.application.dto.HubUpdateRequest;
import com.sparta.hub.application.mapper.HubMapper;
import com.sparta.hub.domain.Hub;
import com.sparta.hub.exception.AlreadyDeletedException;
import com.sparta.hub.infrastructure.repository.HubRepository;
import jakarta.persistence.EntityNotFoundException;
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
public class HubService {

    private final HubRepository hubRepository;
    private final HubMapper hubMapper;

    @CacheEvict(cacheNames = "hubAllCache", allEntries = true)
    public HubResponse createHub(HubCreateRequest hubCreateRequest) {
        Hub hub = hubMapper.createRequestToEntity(hubCreateRequest);
        hubRepository.save(hub);
        return hubMapper.toResponse(hub);
    }

    @CachePut(cacheNames = "hubCache", key = "#result.hubId")
    @CacheEvict(cacheNames = "hubAllCache", allEntries = true)
    public HubResponse updateHub(HubUpdateRequest requestDto, UUID hubId) {
        Hub hub = hubRepository.findByIdAndIsDeleteFalse(hubId)
                .orElseThrow(() -> new EntityNotFoundException("해당 허브를 찾을 수 없습니다"));
        hub.update(requestDto);
        return hubMapper.toResponse(hub);

    }

    @Caching(evict = {
            @CacheEvict(cacheNames = "hubCache", key = "args[0]"),
            @CacheEvict(cacheNames = "hubAllCache", allEntries = true)})
    public void deleteHub(UUID hubId) {
        Hub hub = hubRepository.findById(hubId)
                .orElseThrow(() -> new EntityNotFoundException("해당 허브를 찾을 수 없습니다"));
        if(!hub.getIsDelete()) {
            hub.delete();
        }else{
            throw new AlreadyDeletedException("이미 삭제된 허브입니다");
        }
    }

    @Cacheable(cacheNames = "hubCache", key = "args[0]")
    public HubResponse getSingleHub(UUID hubId) {
        Hub hub = hubRepository.findByIdAndIsDeleteFalse(hubId)
                .orElseThrow(() -> new EntityNotFoundException("해당 허브를 찾을 수 없습니다"));
        return hubMapper.toResponse(hub);
    }

    @Cacheable(cacheNames = "hubAllCache", key = "methodName")
    public Page<HubResponse> getAllHub(Pageable pageable, HubSearchCond cond) {
        Page<HubResponse> list = hubRepository.searchHub(pageable, cond);
        if (list.isEmpty()) {
            throw new EntityNotFoundException("허브가 존재하지 않습니다");
        }
        return list;
    }
}
