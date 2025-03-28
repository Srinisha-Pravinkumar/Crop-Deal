package com.example.demo.controller;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.CropDto;
import com.example.demo.dto.FarmerWrapperDto;
import com.example.demo.dto.UserDto;
import com.example.demo.service.FarmerService;

@RestController
@RequestMapping("farmer")
@CrossOrigin(origins = "*")
public class FarmerController {
	@Autowired
	private FarmerService farmerService;


	@PostMapping("addFarmer")
	public String addUser(@RequestBody UserDto userDto) {
		return farmerService.addUser(userDto);
	}

	@DeleteMapping("deleteFarmer/{id}")
	public String deleteUser(@PathVariable Integer id) {
		return farmerService.deleteUser(id);
	}

	@PutMapping("updateFarmer/{id}")
	public String updateUser(@RequestBody UserDto updatedUserDto, @PathVariable Integer id) {
		return farmerService.updateUser(updatedUserDto, id);
	}


	@GetMapping("getFarmer/{id}")
	public UserDto getUserById(@PathVariable Integer id) {
		return farmerService.getUserById(id);
	}
	
	@PostMapping("{id}/addCrops")
	public void addCrops(@PathVariable int id,@RequestBody List<CropDto> crops) {
		farmerService.addCrops(id, crops);
	}
	
	@GetMapping("getFarmerWrapper/{id}")
	public FarmerWrapperDto getFarmerWrapperById(@PathVariable Integer id) {
		return farmerService.getFarmerWrapperById(id);
	}
	@DeleteMapping("deleteCrop/{id}")
	public void deleteCropById(@PathVariable int id) {
		farmerService.deleteCropById(id);
	}
	
}
