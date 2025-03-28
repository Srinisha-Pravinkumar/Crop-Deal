package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.CropDto;
import com.example.demo.dto.UserDto;
import com.example.demo.feign.FarmerFeign;
import com.example.demo.model.CropModel;
import com.example.demo.repo.CropRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class CropService {

    @Autowired
    private CropRepository cropRepository;

    @Autowired
    private FarmerFeign farmerFeign;
  
    @Transactional
    public void addCrops(int id, List<CropDto> crops) {
        System.out.println("✅ Checking if user with ID " + id + " is a farmer...");

        // Fetch user details using Feign client
        UserDto userDto = farmerFeign.getUserById(id);

        // ✅ Check if user exists and has role "FARMER"
        if (userDto != null && userDto.getRole().equalsIgnoreCase("FARMER")) {
            List<CropModel> cropModelList = new ArrayList<>();

            // ✅ Iterate through CropDto and convert to CropModel
            for (CropDto c : crops) {
                // Skip invalid entries
                if (c == null || c.getCropName() == null || c.getCropQuantity() == null ||
                    c.getCropPrice() == null || c.getCropDescription() == null) {
                    System.out.println("❌ Skipping invalid crop with missing data: " + c);
                    continue;
                }

                // ✅ Create CropModel with cropId as NULL for new entries
                cropModelList.add(new CropModel(
                    null, // Crop ID should be NULL for new entries
                    id,   // Farmer ID
                    c.getCropName(),
                    c.getCropQuantity(),
                    c.getCropPrice(),
                    c.getCropDescription()
                ));
            }

            // ✅ Save only valid crop entries
            if (!cropModelList.isEmpty()) {
                System.out.println("✅ Saving crops to the database for farmer with ID: " + id);
                cropRepository.saveAll(cropModelList);
                System.out.println("✅ Crops added successfully for farmer with ID: " + id);
            } else {
                System.out.println("❌ No valid crops to save. Skipping database insert.");
            }
        } else {
            System.out.println("❌ No Farmer found with this ID or invalid role.");
        }
    }

    // ✅ Get all crops
    public List<CropModel> getCrops() {
        return cropRepository.findAll();
    }

    // ✅ Get crops by farmer ID
    public List<CropModel> getCropsById(int id) {
		return cropRepository.findAllbyId(id);
	}



    // ✅ Delete a crop by farmerId and cropId
	public void deleteByIdAndCropId(Integer farmerId, Integer cropId) {
		cropRepository.deleteByIdAndCropId(farmerId, cropId);
	}

    // ✅ Delete crop by crop ID
    public void deleteCropById(int id) {
        cropRepository.deleteById(id);
        System.out.println("✅ Crop with ID: " + id + " deleted successfully.");
    }
}
