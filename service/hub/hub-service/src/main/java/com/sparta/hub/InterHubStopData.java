/*
package com.sparta.hub;

import com.sparta.hub.application.dto.interhub.InterHubCreateRequest;
import com.sparta.hub.application.mapper.HubMapper;
import com.sparta.hub.application.service.InterHubService;
import com.sparta.hub.domain.InterHub;
import com.sparta.hub.domain.InterHubStop;
import com.sparta.hub.dto.InterHubResponse;
import com.sparta.hub.infrastructure.repository.InterHubStopRepository;
import com.sparta.hub.infrastructure.repository.interhub.InterHubRepository;
import jakarta.annotation.PostConstruct;
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
      InterHubStop interHubStop6 = new InterHubStop(interHub1, 3, UUID.fromString("9e602412-83ea-4b00-b779-09f85b7c0143")
          ,UUID.fromString("2107780c-f197-4a45-aefe-801937b6e3b9"),21L, 24.5);

      interHubStop4.addStop(interHub1);
      interHubStop5.addStop(interHub1);
      interHubStop6.addStop(interHub1);

      interHubStopRepository.save(interHubStop4);
      interHubStopRepository.save(interHubStop5);
      interHubStopRepository.save(interHubStop6);

      List<InterHubResponse> interHubList1 = interHubService.createRoute(
          new InterHubCreateRequest(UUID.fromString("168b0282-5c36-461a-ab21-851341bc7ee7"),
              UUID.fromString("b45e5bca-289f-4c7c-904a-ad397295534e")));
      List<UUID> idList1 = new ArrayList<>();
      for (InterHubResponse interHubResponse : interHubList1) {
        UUID interHubId = interHubResponse.getInterHubId();
        idList1.add(interHubId);
      }
      UUID uuid2 = idList1.get(0);
      InterHub interHub2 = interHubRepository.findById(uuid2).orElse(null);

      InterHubStop interHubStopA = new InterHubStop(interHub2, 1, UUID.fromString("39bddc22-3e38-4bbd-9ac9-6c22c746f125")
          ,UUID.fromString("168b0282-5c36-461a-ab21-851341bc7ee7"),108L, 127.0);
      InterHubStop interHubStopB = new InterHubStop(interHub2, 2, UUID.fromString("779d0d88-68f6-45e8-8fff-5cf51c70f72c")
          ,UUID.fromString("39bddc22-3e38-4bbd-9ac9-6c22c746f125"),87L, 101.6);
      InterHubStop interHubStopC = new InterHubStop(interHub2, 3, UUID.fromString("a0159d31-fb3c-49f9-bd2f-7f1b4488b4b8")
          ,UUID.fromString("779d0d88-68f6-45e8-8fff-5cf51c70f72c"),83L, 97.4);
      InterHubStop interHubStopD = new InterHubStop(interHub2, 4, UUID.fromString("b45e5bca-289f-4c7c-904a-ad397295534e")
          ,UUID.fromString("a0159d31-fb3c-49f9-bd2f-7f1b4488b4b8"), 120L,  140.9);

      UUID uuid3 = idList1.get(1);
      InterHub interHub3 = interHubRepository.findById(uuid3).orElse(null);

      InterHubStop interHubStopH = new InterHubStop(interHub3, 1, UUID.fromString("a0159d31-fb3c-49f9-bd2f-7f1b4488b4b8")
          ,UUID.fromString("b45e5bca-289f-4c7c-904a-ad397295534e"), 120L,  140.9);
      InterHubStop interHubStopG = new InterHubStop(interHub3, 2, UUID.fromString("779d0d88-68f6-45e8-8fff-5cf51c70f72c")
          ,UUID.fromString("a0159d31-fb3c-49f9-bd2f-7f1b4488b4b8"),83L, 97.4);
      InterHubStop interHubStopF = new InterHubStop(interHub3, 3, UUID.fromString("39bddc22-3e38-4bbd-9ac9-6c22c746f125")
          ,UUID.fromString("779d0d88-68f6-45e8-8fff-5cf51c70f72c"),87L, 101.6);
      InterHubStop interHubStopE = new InterHubStop(interHub3, 4, UUID.fromString("168b0282-5c36-461a-ab21-851341bc7ee7")
          ,UUID.fromString("39bddc22-3e38-4bbd-9ac9-6c22c746f125"),108L, 127.0);

      interHubStopRepository.save(interHubStopA);
      interHubStopRepository.save(interHubStopB);
      interHubStopRepository.save(interHubStopC);
      interHubStopRepository.save(interHubStopD);
      interHubStopRepository.save(interHubStopE);
      interHubStopRepository.save(interHubStopF);
      interHubStopRepository.save(interHubStopG);
      interHubStopRepository.save(interHubStopH);

    }
  }
}
*/
