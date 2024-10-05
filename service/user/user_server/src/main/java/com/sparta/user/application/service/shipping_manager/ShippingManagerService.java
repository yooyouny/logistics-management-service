package com.sparta.user.application.service.shipping_manager;

import static com.sparta.user.exception.UserErrorCode.SHIPPING_MANAGER_NOT_FOUND;
import static com.sparta.user.exception.UserErrorCode.SLACK_ID_CONFLICT;

import com.sparta.commons.domain.exception.BusinessException;
import com.sparta.user.application.dto.SippingManagerInfo;
import com.sparta.user.application.service.user.UserService;
import com.sparta.user.domain.model.ShippingManager;
import com.sparta.user.domain.model.User;
import com.sparta.user.domain.model.vo.ShippingManagerType;
import com.sparta.user.domain.repository.shipping_manager.ShippingManagerRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ShippingManagerService {

  private final UserService userService;
  private final ShippingManagerRepository shippingManagerRepository;

  @Transactional
  public void saveShippingManager(
      Long userId, String slackId, ShippingManagerType type, UUID hubId) {
    User user = userService.validateUser(userId);
    this.validateSlackIdUnique(slackId);
    ShippingManager shippingManager = ShippingManager.create(slackId, type, hubId);
    user.assignShippingManagerWithRoleUpgrade(shippingManager);
  }

  private void validateSlackIdUnique(String slackId) {
    if (shippingManagerRepository.existsBySlackId(slackId)) {
      throw new BusinessException(SLACK_ID_CONFLICT);
    }
  }

  @Transactional
  public void updateShippingManager(
      UUID shippingManagerId, String slackId, ShippingManagerType type, UUID hubId) {
    ShippingManager shippingManager = this.validateShippingManager(shippingManagerId);
    if (!shippingManager.getSlackId().equals(slackId)) { // 아이디가 달라지는 경우 유일성 검사
      this.validateSlackIdUnique(slackId);
    }
    shippingManager.update(slackId, type, hubId);
  }

  public ShippingManager validateShippingManager(UUID shippingManagerId) {
    return shippingManagerRepository
        .findById(shippingManagerId)
        .orElseThrow(() -> new BusinessException(SHIPPING_MANAGER_NOT_FOUND));
  }

  @Transactional(readOnly = true)
  public SippingManagerInfo getShippingManagerInfo(UUID shippingManagerId) {
    return shippingManagerRepository
        .findById(shippingManagerId)
        .map(SippingManagerInfo::create)
        .orElseThrow(() -> new BusinessException(SHIPPING_MANAGER_NOT_FOUND));
  }

  @Transactional(readOnly = true)
  public Page<SippingManagerInfo> getShippingManagerInfos(
      Long userId, String role, String keyword, Pageable pageable) {
    User user = userService.validateUser(userId);
    UUID shippingManagerId =
        role.equals("ROLE_HUB_MANAGER") ? user.getShippingManager().getId() : null;
    return shippingManagerRepository.findShippingManagerInfos(shippingManagerId, keyword, pageable);
  }

  @Transactional
  public void deleteShippingManager(UUID shippingManagerId, String username) {
    ShippingManager shippingManager = this.validateShippingManager(shippingManagerId);
    shippingManager.softDelete(username);
  }
}
