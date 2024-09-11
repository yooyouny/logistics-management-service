package com.sparta.hub;

import com.sparta.hub.application.dto.interhub.InterHubCreateRequest;
import com.sparta.hub.application.dto.interhub.InterHubResponse;
import com.sparta.hub.application.dto.interhub.InterHubStopResponse;
import com.sparta.hub.application.mapper.HubMapper;
import com.sparta.hub.application.mapper.InterHubMapper;
import com.sparta.hub.application.service.InterHubService;
import com.sparta.hub.domain.Hub;
import com.sparta.hub.domain.InterHub;
import com.sparta.hub.domain.InterHubStop;
import com.sparta.hub.infrastructure.repository.InterHubStopRepository;
import com.sparta.hub.infrastructure.repository.interhub.InterHubRepository;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.beans.PropertyVetoException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class InterHubStopData {

  private final TestDataService testDataService;
  private final HubMapper hubMapper;

  @PostConstruct
  public void init() {
    testDataService.init();
  }

  @Component
  @RequiredArgsConstructor
  static class TestDataService {

    private final InterHubService interHubService;
    private final InterHubRepository interHubRepository;
    private final InterHubStopRepository interHubStopRepository;

    @Transactional
    public void init() {
      List<InterHubResponse> interHubList = interHubService.createRoute(
          new InterHubCreateRequest(UUID.fromString("2107780c-f197-4a45-aefe-801937b6e3b9"),
              UUID.fromString("6968a78e-897e-47f4-aed5-f6e846896797")));
      List<UUID> idList = new ArrayList<>();
      for (InterHubResponse interHubResponse : interHubList) {
        UUID interHubId = interHubResponse.getInterHubId();
        idList.add(interHubId);
      }
      UUID uuid = idList.get(0);
      InterHub interHub = interHubRepository.findById(uuid).orElse(null);
      System.out.println("interHub = " + interHub.getInterHubId());
      System.out.println(
          "interHub.getInterHubStops().size() = " + interHub.getInterHubStops().size());
      InterHubStop interHubStop1 = new InterHubStop(interHub, 1, UUID.fromString("2107780c-f197-4a45-aefe-801937b6e3b9")
      ,UUID.fromString("9e602412-83ea-4b00-b779-09f85b7c0143"),21L, 24.5);
      InterHubStop interHubStop2 = new InterHubStop(interHub, 2, UUID.fromString("9e602412-83ea-4b00-b779-09f85b7c0143")
          ,UUID.fromString("31092785-cc0f-45c1-9952-10e98e0abb0b"),57L, 67.2);
      InterHubStop interHubStop3 = new InterHubStop(interHub, 3, UUID.fromString("31092785-cc0f-45c1-9952-10e98e0abb0b")
          ,UUID.fromString("6968a78e-897e-47f4-aed5-f6e846896797"),236L, 276.2);

      interHubStop1.addStop(interHub);
      interHubStop2.addStop(interHub);
      interHubStop3.addStop(interHub);

      interHubStopRepository.save(interHubStop1);
      interHubStopRepository.save(interHubStop2);
      interHubStopRepository.save(interHubStop3);

      UUID uuid1 = idList.get(1);
      InterHub interHub1 = interHubRepository.findById(uuid1).orElse(null);
      InterHubStop interHubStop4 = new InterHubStop(interHub1, 1, UUID.fromString("6968a78e-897e-47f4-aed5-f6e846896797")
          ,UUID.fromString("31092785-cc0f-45c1-9952-10e98e0abb0b"),236L, 276.2);
      InterHubStop interHubStop5 = new InterHubStop(interHub1, 2, UUID.fromString("31092785-cc0f-45c1-9952-10e98e0abb0b")
          ,UUID.fromString("9e602412-83ea-4b00-b779-09f85b7c0143"),57L, 67.2);
      InterHubStop interHubStop6 = new InterHubStop(interHub, 3, UUID.fromString("9e602412-83ea-4b00-b779-09f85b7c0143")
          ,UUID.fromString("2107780c-f197-4a45-aefe-801937b6e3b9"),21L, 24.5);

      interHubStop4.addStop(interHub1);
      interHubStop5.addStop(interHub1);
      interHubStop6.addStop(interHub1);

      interHubStopRepository.save(interHubStop4);
      interHubStopRepository.save(interHubStop5);
      interHubStopRepository.save(interHubStop6);

    }
  }
}
