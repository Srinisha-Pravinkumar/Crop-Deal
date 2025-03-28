package com.example.demo.service;
 
import com.example.demo.dto.CropDto;
import com.example.demo.dto.FarmerWrapperDto;
import com.example.demo.dto.UserDto;
import com.example.demo.feign.CropFeign;
import com.example.demo.feign.DealerFeign;
import com.example.demo.feign.FarmerFeign;
 
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
 
import java.util.Arrays;
import java.util.List;
 
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
 
class DealerServiceTest {
 
    @Mock
    private DealerFeign dealerFeign;
 
    @Mock
    private CropFeign cropFeign;
 
    @Mock
    private FarmerFeign farmerFeign;
 
    @InjectMocks
    private DealerService dealerService;
 
    private UserDto userDto;
    private CropDto cropDto;
    private FarmerWrapperDto farmerWrapperDto;
 
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
 
        // Initialize UserDto
        userDto = new UserDto();
        userDto.setId(1);
        userDto.setFullName("John Dealer");
        userDto.setEmailId("john.dealer@example.com");
        userDto.setRole("DEALER");
 
        // Initialize CropDto
        cropDto = new CropDto();
        cropDto.setCropId(101);
        cropDto.setCropName("Wheat");
        cropDto.setCropQuantity("100 kg");
        cropDto.setCropPrice(150.0);
        cropDto.setCropDescription("High-quality wheat");
 
        // Initialize FarmerWrapperDto
        farmerWrapperDto = new FarmerWrapperDto();
        farmerWrapperDto.setFullname("Farmer John");
        farmerWrapperDto.setAddress("123 Farm Street");
        farmerWrapperDto.setMobileNumber("1234567890");
    }
 

    @Test
    void testUpdateUser() {
        // Mock the Feign client updateUser to return a success message
        when(dealerFeign.updateUser(any(UserDto.class), eq(1)))
                .thenReturn("Updated Successfully");
 
        // Call the updateUser method from the service
        String result = dealerService.updateUser(userDto, 1);
 
        // Verify Feign client interaction
        verify(dealerFeign, times(1)).updateUser(userDto, 1);
        assertEquals("Updated Successfully", result);
    }
 
    @Test
    void testGetAllCrops() {
        // Mock the cropFeign to return a list of CropDto
        List<CropDto> crops = Arrays.asList(cropDto);
        when(cropFeign.getCrops()).thenReturn(crops);
 
        // Call the getAllCrops method from the service
        List<CropDto> result = dealerService.getAllCrops();
 
        // Verify Feign client interaction
        verify(cropFeign, times(1)).getCrops();
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Wheat", result.get(0).getCropName());
    }
 
    @Test
    void testGetFarmerById() {
        // Mock the farmerFeign to return FarmerWrapperDto
        when(farmerFeign.getFarmerWrapperById(1)).thenReturn(farmerWrapperDto);
 
        // Call the getFarmerById method from the service
        FarmerWrapperDto result = dealerService.getFarmerById(1);
 
        // Verify Feign client interaction
        verify(farmerFeign, times(1)).getFarmerWrapperById(1);
        assertNotNull(result);
        assertEquals("Farmer John", result.getFullname());
        assertEquals("123 Farm Street", result.getAddress());
        assertEquals("1234567890", result.getMobileNumber());
    }
}



//@Test
//void testDeleteUser() {
//  // Mock the Feign client deleteUser (void method)
//  doNothing().when(dealerFeign).deleteUser(1);
//
//  // Call the deleteUser method from the service
//  String result = dealerService.deleteUser(1);
//
//  // Verify that the Feign client deleteUser method was called once
//  verify(dealerFeign, times(1)).deleteUser(1);
//
//  // Assert that the result is the expected success message
//  assertEquals("Deleted Successfully", result);
//}
//
 




//package com.example.demo.service;
//
//import com.example.demo.dto.CropDto;
//import com.example.demo.dto.FarmerWrapperDto;
//import com.example.demo.dto.UserDto;
//import com.example.demo.feign.CropFeign;
//import com.example.demo.feign.DelaerFeign;
//import com.example.demo.feign.FarmerFeign;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//
//import java.util.Arrays;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//class DealerServiceTest {
//
//    @Mock
//    private DelaerFeign delaerFeign;
//
//    @Mock
//    private CropFeign cropFeign;
//
//    @Mock
//    private FarmerFeign farmerFeign;
//
//    @InjectMocks
//    private DealerService dealerService;
//
//    private UserDto userDto;
//    private CropDto cropDto;
//    private FarmerWrapperDto farmerWrapperDto;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//
//        // Initialize UserDto
//        userDto = new UserDto();
//        userDto.setId(1);
//        userDto.setFullName("John Dealer");
//        userDto.setEmailId("john.dealer@example.com");
//        userDto.setRole("DEALER");
//
//        // Initialize CropDto
//        cropDto = new CropDto();
//        cropDto.setCropId(101);
//        cropDto.setCropName("Wheat");
//        cropDto.setCropQuantity("100 kg");
//        cropDto.setCropPrice(150.0);
//        cropDto.setCropDescription("High-quality wheat");
//
//        // Initialize FarmerWrapperDto
//        farmerWrapperDto = new FarmerWrapperDto();
//        farmerWrapperDto.setFullname("Farmer John");
//        farmerWrapperDto.setAddress("123 Farm Street");
//        farmerWrapperDto.setMobileNumber("1234567890");
//    }
//
//    @Test
//    void testDeleteUser() {
//        // Mock the feign client deleteUser
//        doNothing().when(delaerFeign).deleteUser(1);
//
//        String result = dealerService.deleteUser(1);
//
//        // Verify the feign client interaction
//        verify(delaerFeign, times(1)).deleteUser(1);
//        assertEquals("Deleted Succesfully", result);
//    }
//
//    @Test
//    void testUpdateUser() {
//        // Mock the feign client updateUser
//        when(delaerFeign.updateUser(any(UserDto.class), eq(1)))
//                .thenReturn("Updated Successfully");
//
//        String result = dealerService.updateUser(userDto, 1);
//
//        // Verify feign client interaction
//        verify(delaerFeign, times(1)).updateUser(userDto, 1);
//        assertEquals("Updated Successfully", result);
//    }
//
//    @Test
//    void testGetAllCrops() {
//        // Mock the cropFeign to return a list of CropDto
//        List<CropDto> crops = Arrays.asList(cropDto);
//        when(cropFeign.getCrops()).thenReturn(crops);
//
//        List<CropDto> result = dealerService.getAllCrops();
//
//        // Verify feign client interaction
//        verify(cropFeign, times(1)).getCrops();
//        assertNotNull(result);
//        assertEquals(1, result.size());
//        assertEquals("Wheat", result.get(0).getCropName());
//    }
//
//    @Test
//    void testGetFarmerById() {
//        // Mock the farmerFeign to return FarmerWrapperDto
////        when(FarmerFeign.getFarmerWrapperById(1)).thenReturn(farmerWrapperDto);
//
//        FarmerWrapperDto result = dealerService.getFarmerById(1);
//
//        verify(farmerFeign, times(1));
//		// Verify feign client interaction
////        FarmerFeign.getFarmerWrapperById(1);
//        assertNotNull(result);
//        assertEquals("Farmer John", result.getFullname());
//        assertEquals("123 Farm Street", result.getAddress());
//        assertEquals("1234567890", result.getMobileNumber());
//    }
//}
