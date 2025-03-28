package com.example.demo.service;
 
import com.example.demo.FarmerServiceApplication;
 
import com.example.demo.dto.CropDto;
 
import com.example.demo.dto.FarmerWrapperDto;
 
import com.example.demo.dto.UserDto;
 
import com.example.demo.exception.UserTypeMismatchException;
 
import com.example.demo.feign.CropFeign;
 
import com.example.demo.feign.FarmerFeign;
 
import com.example.demo.service.FarmerService;
 
import org.junit.jupiter.api.BeforeEach;
 
import org.junit.jupiter.api.Test;
 
import org.mockito.InjectMocks;
 
import org.mockito.Mock;
 
import org.mockito.MockitoAnnotations;
 
import org.springframework.beans.factory.annotation.Autowired;
 
import org.springframework.boot.test.context.SpringBootTest;
 
import org.springframework.boot.test.mock.mockito.MockBean;
 
import java.util.Arrays;
 
import java.util.List;
 
import static org.junit.jupiter.api.Assertions.*;
 
import static org.mockito.Mockito.*;
 
@SpringBootTest(classes = FarmerServiceApplication.class)
 
class FarmerServiceTest {
 
    @MockBean
 
    private FarmerFeign farmerFeign;
 
    @MockBean
 
    private CropFeign cropFeign;
 
    @InjectMocks
 
    private FarmerService farmerService;
 
    private UserDto farmerUserDto;
 
    private UserDto nonFarmerUserDto;
 
    private CropDto cropDto;
 
    @BeforeEach
 
    void setUp() {
 
        MockitoAnnotations.openMocks(this);
 
        farmerUserDto = new UserDto();
 
        farmerUserDto.setId(1);
 
        farmerUserDto.setFullName("John Farmer");
 
        farmerUserDto.setRole("FARMER");
 
        farmerUserDto.setMobileNumber("1234567890");
 
        nonFarmerUserDto = new UserDto();
 
        nonFarmerUserDto.setId(2);
 
        nonFarmerUserDto.setFullName("John Dealer");
 
        nonFarmerUserDto.setRole("DEALER");
 
        cropDto = new CropDto();
 
        cropDto.setCropId(101);
 
        cropDto.setCropName("Wheat");
 
        cropDto.setCropQuantity("100 kg");
 
        cropDto.setCropPrice(150.0);
 
        cropDto.setCropDescription("High-quality wheat");
 
    }
 
    @Test
 
    void testAddUser_Success() {
 
        // Mock the Feign client behavior
 
        when(farmerFeign.addUser(farmerUserDto)).thenReturn("Farmer added successfully");
 
        String result = farmerService.addUser(farmerUserDto);
 
        // Verify and assert
 
        verify(farmerFeign, times(1)).addUser(farmerUserDto);
 
        assertEquals("Farmer added successfully", result);
 
    }
 
    @Test
 
    void testAddUser_UserTypeMismatchException() {
 
        // Assert exception for non-farmer user
 
        UserTypeMismatchException exception = assertThrows(UserTypeMismatchException.class, () -> {
 
            farmerService.addUser(nonFarmerUserDto);
 
        });
 
        assertEquals("User type shoud be Farmer", exception.getMessage());
 
        verify(farmerFeign, never()).addUser(any());
 
    }
 
    @Test
 
    void testGetUsers() {
 
        // Mock Feign client to return a list of users
 
        List<UserDto> users = Arrays.asList(farmerUserDto, nonFarmerUserDto);
 
        when(farmerFeign.getUsers()).thenReturn(users);
 
        List<UserDto> result = farmerService.getUsers();
 
        // Verify and assert
 
        verify(farmerFeign, times(1)).getUsers();
 
        assertEquals(2, result.size());
 
        assertEquals("John Farmer", result.get(0).getFullName());
 
    }
 
    @Test
 
    void testGetUserById() {
 
        // Mock Feign client to return a specific user
 
        when(farmerFeign.getUserById(1)).thenReturn(farmerUserDto);
 
        UserDto result = farmerService.getUserById(1);
 
        // Verify and assert
 
        verify(farmerFeign, times(1)).getUserById(1);
 
        assertNotNull(result);
 
        assertEquals("John Farmer", result.getFullName());
 
    }
 
    @Test
 
    void testDeleteUser() {
 
        // Mock Feign client behavior
 
    	doReturn("Deleted Successfully").when(farmerFeign).deleteUser(1);
 
        String result = farmerService.deleteUser(1);
 
        // Verify and assert
 
        verify(farmerFeign, times(1)).deleteUser(1);
 
        assertEquals("Deleted Succesfully", result);
 
    }
 
    @Test
 
    void testUpdateUser() {
 
        // Mock Feign client behavior
 
        when(farmerFeign.updateUser(farmerUserDto, 1)).thenReturn("updated succesfully");
 
        // Call the method
 
        String result = farmerService.updateUser(farmerUserDto, 1);
 
        // Verify and assert
 
        verify(farmerFeign, times(1)).updateUser(farmerUserDto, 1);
 
        assertEquals("updated succesfully", result);
 
    }
 
 
    @Test
 
    void testAddCrops() {
 
        // Mock UserDto with valid farmer role
 
        UserDto farmerUserDto = new UserDto();
 
        farmerUserDto.setId(1);
 
        farmerUserDto.setRole("FARMER");
 
        farmerUserDto.setFullName("John Doe");
 
        // Mock CropDto with valid crop details
 
        CropDto cropDto = new CropDto();
 
        cropDto.setCropName("Wheat");
 
        cropDto.setCropQuantity("100 kg");
 
        // Mock getUserById(1) to return a valid farmer
 
        when(farmerFeign.getUserById(1)).thenReturn(farmerUserDto);
 
        // Mock addCrops(1, List<CropDto>) to do nothing
 
        doNothing().when(cropFeign).addCrops(eq(1), anyList());
 
        // Call the method
 
        farmerService.addCrops(1, Arrays.asList(cropDto));
 
        // Verify the interactions
 
        verify(farmerFeign, times(1)).getUserById(1);
 
        verify(cropFeign, times(1)).addCrops(1, Arrays.asList(cropDto));
 
    }
 
 
    @Test
 
    void testGetFarmerWrapperById() {
 
        // Mock Feign client behavior
 
        when(farmerFeign.getUserById(1)).thenReturn(farmerUserDto);
 
        FarmerWrapperDto result = farmerService.getFarmerWrapperById(1);
 
        // Verify and assert
 
        verify(farmerFeign, times(1)).getUserById(1);
 
        assertNotNull(result);
 
        assertEquals("John Farmer", result.getFullname());
 
        assertEquals("1234567890", result.getMobileNumber());
 
    }
 
    @Test
 
    void testDeleteCropById() {
 
        // Mock Feign client behavior
 
        doNothing().when(cropFeign).deleteCropById(101);
 
        farmerService.deleteCropById(101);
 
        // Verify
 
        verify(cropFeign, times(1)).deleteCropById(101);
 
    }
 
}
 
 








//package com.example.demo.service;
//
//import com.example.demo.FarmerServiceApplication;
//import com.example.demo.dto.CropDto;
//import com.example.demo.dto.FarmerWrapperDto;
//import com.example.demo.dto.UserDto;
//import com.example.demo.exception.UserTypeMismatchException;
//import com.example.demo.feign.CropFeign;
//import com.example.demo.feign.FarmerFeign;
//import com.example.demo.service.FarmerService;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.util.Arrays;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//@SpringBootTest(classes = FarmerServiceApplication.class)
//class FarmerServiceTest {
//
//    @Mock
//    private FarmerFeign farmerFeign;
//
//    @Mock
//    private CropFeign cropFeign;
//
//    @InjectMocks
//    private FarmerService farmerService;
//
//    private UserDto farmerUserDto;
//    private UserDto nonFarmerUserDto;
//    private CropDto cropDto;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//
//        farmerUserDto = new UserDto();
//        farmerUserDto.setId(1);
//        farmerUserDto.setFullName("John Farmer");
//        farmerUserDto.setRole("FARMER");
//        farmerUserDto.setMobileNumber("1234567890");
//
//        nonFarmerUserDto = new UserDto();
//        nonFarmerUserDto.setId(2);
//        nonFarmerUserDto.setFullName("John Dealer");
//        nonFarmerUserDto.setRole("DEALER");
//
//        cropDto = new CropDto();
//        cropDto.setCropId(101);
//        cropDto.setCropName("Wheat");
//        cropDto.setCropQuantity("100 kg");
//        cropDto.setCropPrice(150.0);
//        cropDto.setCropDescription("High-quality wheat");
//    }
//
//    @Test
//    void testAddUser_Success() {
//        // Mock the Feign client behavior
//        when(farmerFeign.addUser(farmerUserDto)).thenReturn("Farmer added successfully");
//
//        String result = farmerService.addUser(farmerUserDto);
//
//        // Verify and assert
//        verify(farmerFeign, times(1)).addUser(farmerUserDto);
//        assertEquals("Farmer added successfully", result);
//    }
//
//    @Test
//    void testAddUser_UserTypeMismatchException() {
//        // Assert exception for non-farmer user
//        UserTypeMismatchException exception = assertThrows(UserTypeMismatchException.class, () -> {
//            farmerService.addUser(nonFarmerUserDto);
//        });
//
//        assertEquals("User type shoud be Farmer", exception.getMessage());
//        verify(farmerFeign, never()).addUser(any());
//    }
//
//    @Test
//    void testGetUsers() {
//        // Mock Feign client to return a list of users
//        List<UserDto> users = Arrays.asList(farmerUserDto, nonFarmerUserDto);
//        when(farmerFeign.getUsers()).thenReturn(users);
//
//        List<UserDto> result = farmerService.getUsers();
//
//        // Verify and assert
//        verify(farmerFeign, times(1)).getUsers();
//        assertEquals(2, result.size());
//        assertEquals("John Farmer", result.get(0).getFullName());
//    }
//
//    @Test
//    void testGetUserById() {
//        // Mock Feign client to return a specific user
//        when(farmerFeign.getUserById(1)).thenReturn(farmerUserDto);
//
//        UserDto result = farmerService.getUserById(1);
//
//        // Verify and assert
//        verify(farmerFeign, times(1)).getUserById(1);
//        assertNotNull(result);
//        assertEquals("John Farmer", result.getFullName());
//    }
//
//    @Test
//    void testDeleteUser() {
//        // Mock Feign client behavior
//        doNothing().when(farmerFeign).deleteUser(1);
//
//        String result = farmerService.deleteUser(1);
//
//        // Verify and assert
//        verify(farmerFeign, times(1)).deleteUser(1);
//        assertEquals("Deleted Succesfully", result);
//    }
//
//    @Test
//    void testUpdateUser() {
//        // Mock Feign client behavior
//        doNothing().when(farmerFeign).updateUser(farmerUserDto, 1);
//
//        String result = farmerService.updateUser(farmerUserDto, 1);
//
//        // Verify and assert
//        verify(farmerFeign, times(1)).updateUser(farmerUserDto, 1);
//        assertEquals("updated succesfully", result);
//    }
//
//    @Test
//    void testAddCrops() {
//        // Mock Feign client behavior
//        doNothing().when(cropFeign).addCrops(1, Arrays.asList(cropDto));
//
//        farmerService.addCrops(1, Arrays.asList(cropDto));
//
//        // Verify
//        verify(cropFeign, times(1)).addCrops(1, Arrays.asList(cropDto));
//    }
//
//    @Test
//    void testGetFarmerWrapperById() {
//        // Mock Feign client behavior
//        when(farmerFeign.getUserById(1)).thenReturn(farmerUserDto);
//
//        FarmerWrapperDto result = farmerService.getFarmerWrapperById(1);
//
//        // Verify and assert
//        verify(farmerFeign, times(1)).getUserById(1);
//        assertNotNull(result);
//        assertEquals("John Farmer", result.getFullname());
//        assertEquals("1234567890", result.getMobileNumber());
//    }
//
//    @Test
//    void testDeleteCropById() {
//        // Mock Feign client behavior
//        doNothing().when(cropFeign).deleteCropById(101);
//
//        farmerService.deleteCropById(101);
//
//        // Verify
//        verify(cropFeign, times(1)).deleteCropById(101);
//    }
//}
