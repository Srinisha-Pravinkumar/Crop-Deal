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
import com.example.demo.service.DealerService;

@RestController
@RequestMapping("dealer")

@CrossOrigin(origins = "*")
public class DealerController {

	@Autowired
	private DealerService dealerService;
	
	@Autowired
	private DealerController(DealerService dealerService) {
		this.dealerService=dealerService;
	}

//	@PostMapping("addDelaer")
//	public String addUser(@RequestBody UserDto userModel) {
//		return dealerService.addUser(userModel);
//	}

	@DeleteMapping("deleteDealer/{id}")
	public String deleteUser(@PathVariable Integer id) {
		return dealerService.deleteUser(id);
	}

	@PutMapping("updateDealer/{id}")
	public String updateUser(@RequestBody UserDto updatedUserModel, @PathVariable Integer id) {
		return dealerService.updateUser(updatedUserModel, id);
	}
	@GetMapping("getAllcrops")
	public List<CropDto> getAllCrops(){
		return dealerService.getAllCrops();
	}
	
	@GetMapping("getFarmer/{id}")
	public FarmerWrapperDto getFarmerById(@PathVariable Integer id) {
		return dealerService.getFarmerById(id);
	}

}