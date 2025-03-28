package com.example.demo.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.example.demo.dto.UserDto;  // Import UserDto


@FeignClient(name = "login-service") // Match exactly with Eureka registration
public interface FarmerFeign {
    @GetMapping("/profiles/getUser/{id}")
    UserDto getUserById(@PathVariable Integer id);
}


