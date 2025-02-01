package com.david.mbaimbai.service;

import com.david.mbaimbai.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "USER-SERVICE", url = "http://localhost:9888")
public interface UserService {
    @GetMapping("/user/profile")
    public UserDto getUserProfile(@RequestHeader("Authorization") String jwt);
}
