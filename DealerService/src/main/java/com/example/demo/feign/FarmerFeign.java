package com.example.demo.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.demo.dto.FarmerWrapperDto;

@FeignClient("FarmerService")
public interface FarmerFeign {
//	@GetMapping("farmer/getFarmer/{id}")
//	public UserDto getUserById(@PathVariable Integer id);

	@GetMapping("farmer/getFarmerWrapper/{id}")
	
	
	FarmerWrapperDto getFarmerWrapperById(@PathVariable Integer id);

}
