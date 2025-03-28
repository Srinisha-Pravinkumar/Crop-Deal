package com.example.demo.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.dto.CropDto;

@FeignClient("CropService")
public interface CropFeign {
	@GetMapping("crop/getAllcrops")
	public List<CropDto> getCrops();
}
