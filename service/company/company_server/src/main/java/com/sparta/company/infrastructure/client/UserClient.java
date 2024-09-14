package com.sparta.company.infrastructure.client;

import com.sparta.user.dto.user_dto.UserDto;
import java.util.Optional;
import javax.naming.Name;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "user-service")
public interface UserClient {

  @GetMapping("/internal/users")
  Optional<UserDto> getUserDto(@RequestParam(value = "username") String username);

}
