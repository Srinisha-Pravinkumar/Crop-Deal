package com.example.demo.service;

import java.util.List;
import org.springframework.amqp.rabbit.core.RabbitTemplate;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.CropDto;
import com.example.demo.dto.FarmerWrapperDto;
import com.example.demo.dto.UserDto;
import com.example.demo.exception.UserTypeMismatchException;
import com.example.demo.feign.CropFeign;
import com.example.demo.feign.FarmerFeign;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class FarmerService {
	@Autowired
	private FarmerFeign feign;
	@Autowired
	private CropFeign cropFeign;

	public String addUser(UserDto userDto) {	
		if(userDto.getRole().equalsIgnoreCase("farmer")) {
			return feign.addUser(userDto);
		}else {
			throw new UserTypeMismatchException("User type shoud be Farmer");	
		}
	}

	public List<UserDto> getUsers() {
		return feign.getUsers();
	}

	public UserDto getUserById(Integer id) {
		return feign.getUserById(id);
	}

	public String deleteUser(Integer id) {
		feign.deleteUser(id);
		return "Deleted Succesfully";
	}

	public String updateUser(UserDto updatedUserModel, Integer id) {
		feign.updateUser(updatedUserModel, id);
		return "updated succesfully";
	}
	//exception not added Yet
	public void addCrops(int id, List<CropDto> crops) {
	    // Fetch the user by ID
	    UserDto userModel = feign.getUserById(id);

	    // Check if user exists
	    if (userModel == null) {
	        throw new RuntimeException("User not found with ID: " + id);
	    }

	    // Validate if the role is FARMER
	    if (!"FARMER".equalsIgnoreCase(userModel.getRole())) {
	        throw new RuntimeException("Crops can only be added by a user with role FARMER. Role found: " + userModel.getRole());
	    }

	    // Check if crop list is valid (optional validation)
	    if (crops == null || crops.isEmpty()) {
	        throw new IllegalArgumentException("Crop list cannot be empty");
	    }

	    // Validate each crop entry (optional validation)
	    for (CropDto crop : crops) {
	        if (crop.getCropName() == null || crop.getCropName().isEmpty()) {
	            throw new IllegalArgumentException("Crop name is required");
	        }
	        if (crop.getCropQuantity() == null || crop.getCropQuantity().isEmpty()) {
	            throw new IllegalArgumentException("Crop quantity is required");
	        }
	    }

	    // Add crops if everything is valid
	    cropFeign.addCrops(id, crops);
	}

	//exception not added Yet
	public FarmerWrapperDto getFarmerWrapperById(Integer id) {
		UserDto userModel= feign.getUserById(id);

		FarmerWrapperDto farmerWrapperDto=new FarmerWrapperDto();
		farmerWrapperDto.setFullname(userModel.getFullName());
		farmerWrapperDto.setAddress(userModel.getAddress());
		farmerWrapperDto.setMobileNumber(userModel.getMobileNumber());
		return farmerWrapperDto;
	}
	//exception not added Yet
	public void deleteCropById(int id) {
		cropFeign.deleteCropById(id);
	}
	@Autowired
	private RabbitTemplate rabbitTemplate;

	// Notify Dealer When Crop Added
	public void notifyDealersAboutCrop(CropDto cropDto) {
	    rabbitTemplate.convertAndSend("crop_notification_queue", cropDto);
	}



}
