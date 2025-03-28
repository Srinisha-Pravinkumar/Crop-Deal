package com.example.demo.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.demo.dto.CropDto;


@FeignClient("CropService")
public interface CropFeign {
	@PostMapping("crop/farmer/{id}/addCrops")
	public void addCrops(@PathVariable int id,@RequestBody List<CropDto> crops);
	@DeleteMapping("crop/deleteCropById/{id}")
	public void deleteCropById(@PathVariable int id);
}
