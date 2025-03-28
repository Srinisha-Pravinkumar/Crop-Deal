package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.CropDto;
import com.example.demo.dto.FarmerWrapperDto;
import com.example.demo.dto.UserDto;
import com.example.demo.feign.CropFeign;
import com.example.demo.feign.DealerFeign;
import com.example.demo.feign.FarmerFeign;


@Service
public class DealerService {
	//Admin
	@Autowired 
	private DealerFeign feign;

	//Crop
	@Autowired
	private CropFeign cropFeign;
	
	//Farmer
	@Autowired

	  private FarmerFeign farmerFeign; 

//		public String addUser(UserDto userModel) {	
//			feign.addUser(userModel);
//			return userModel.toString();
//		}
		public String deleteUser(Integer id) {
			feign.deleteUser(id);
			return "Deleted Succesfully";
		
	}

		public String updateUser(UserDto updatedUserModel, Integer id) {
			
		return feign.updateUser(updatedUserModel, id);
			
		}
		public List<CropDto> getAllCrops() {
			return cropFeign.getCrops();
		}
		public FarmerWrapperDto getFarmerById(Integer id) {
		    return farmerFeign.getFarmerWrapperById(id); // ✅ Correct
		}


		

}
