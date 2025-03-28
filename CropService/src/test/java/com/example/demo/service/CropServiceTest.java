package com.example.demo.service;

import com.example.demo.dto.CropDto;
import com.example.demo.dto.UserDto;
import com.example.demo.feign.FarmerFeign;
import com.example.demo.model.CropModel;
import com.example.demo.repo.CropRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CropServiceTest {

    @Mock
    private CropRepository cropRepository;

    @Mock
    private FarmerFeign farmerFeign;

    @InjectMocks
    private CropService cropService;

    private UserDto farmerUser;
    private CropDto cropDto;
    private CropModel cropModel;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Setup Farmer UserDto
        farmerUser = new UserDto();
        farmerUser.setId(1);
        farmerUser.setRole("farmer");

        // Setup CropDto
        cropDto = new CropDto();
        cropDto.setCropId(101);
        cropDto.setCropName("Wheat");
        cropDto.setCropQuantity("100 kg");
        cropDto.setCropPrice(150.0);
        cropDto.setCropDescription("High-quality wheat");

        // Setup CropModel
        cropModel = new CropModel(101, 1, "Wheat", "100 kg", 150.0, "High-quality wheat");
    }
    
    @Test
    void testAddCrops_Success() {
        // Mock FarmerFeign to return a valid farmer user
        when(farmerFeign.getUserById(1)).thenReturn(farmerUser);
 
        // Mock CropRepository to save crops
        when(cropRepository.saveAll(anyList())).thenReturn(Arrays.asList(cropModel));  // Mocking return value
        // Call addCrops method
        List<CropDto> crops = Arrays.asList(cropDto);
        cropService.addCrops(1, crops);
 
        // Verify FarmerFeign was called
        verify(farmerFeign, times(1)).getUserById(1);
 
        // Verify CropRepository.saveAll() was called
        verify(cropRepository, times(1)).saveAll(anyList());
    }

//    @Test
//    void testAddCrops_Success() {
//        // Mock FarmerFeign to return a valid farmer user
//        when(farmerFeign.getUserById(1)).thenReturn(farmerUser);
//
//        // Mock CropRepository to save crops
//        doNothing().when(cropRepository).saveAll(anyList());
//
//        // Call addCrops method
//        cropService.addCrops(1, Arrays.asList(cropDto));
//
//        // Verify FarmerFeign was called
//        verify(farmerFeign, times(1)).getUserById(1);
//
//        // Verify CropRepository.saveAll() was called
//        verify(cropRepository, times(1)).saveAll(anyList());
//    }

    @Test
    void testAddCrops_FarmerNotExist() {
        // Mock FarmerFeign to return a user who is not a farmer
        UserDto nonFarmer = new UserDto();
        nonFarmer.setId(2);
        nonFarmer.setRole("dealer");

        when(farmerFeign.getUserById(1)).thenReturn(nonFarmer);

        // Call addCrops method
        cropService.addCrops(1, Arrays.asList(cropDto));

        // Verify that FarmerFeign was called
        verify(farmerFeign, times(1)).getUserById(1);

        // Verify that CropRepository.saveAll() was NOT called
        verify(cropRepository, never()).saveAll(anyList());
    }

    @Test
    void testGetCrops() {
        // Mock CropRepository to return a list of crops
        when(cropRepository.findAll()).thenReturn(Arrays.asList(cropModel));

        // Call getCrops method
        List<CropModel> result = cropService.getCrops();

        // Assertions
        assertEquals(1, result.size());
        assertEquals("Wheat", result.get(0).getCropName());

        // Verify CropRepository.findAll() was called
        verify(cropRepository, times(1)).findAll();
    }

    @Test
    void testGetCropsById() {
        // Mock CropRepository to return a list of crops for a specific farmer ID
        when(cropRepository.findAllbyId(1)).thenReturn(Arrays.asList(cropModel));

        // Call getCropsById method
        List<CropModel> result = cropService.getCropsById(1);

        // Assertions
        assertEquals(1, result.size());
        assertEquals("Wheat", result.get(0).getCropName());

        // Verify CropRepository.findAllbyId() was called
        verify(cropRepository, times(1)).findAllbyId(1);
    }

    @Test
    void testDeleteByIdAndCropId() {
        // Mock CropRepository to perform delete operation
        doNothing().when(cropRepository).deleteByIdAndCropId(1, 101);

        // Call deleteByIdAndCropId method
        cropService.deleteByIdAndCropId(1, 101);

        // Verify that CropRepository.deleteByIdAndCropId() was called
        verify(cropRepository, times(1)).deleteByIdAndCropId(1, 101);
    }

    @Test
    void testDeleteCropById() {
        // Mock CropRepository to perform delete operation
        doNothing().when(cropRepository).deleteById(101);

        // Call deleteCropById method
        cropService.deleteCropById(101);

        // Verify that CropRepository.deleteById() was called
        verify(cropRepository, times(1)).deleteById(101);
    }
}
