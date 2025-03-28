package com.example.demo.controller;
 
import com.example.demo.dto.CropDto;
import com.example.demo.dto.FarmerWrapperDto;
import com.example.demo.dto.UserDto;
import com.example.demo.service.FarmerService;
import com.fasterxml.jackson.databind.ObjectMapper;
 
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
 
import java.util.Arrays;
import java.util.List;
 
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
 
@WebMvcTest(FarmerController.class)
public class FarmerControllerTest {
 
    @Autowired
    private MockMvc mockMvc;
 
    @MockBean
    private FarmerService farmerService;
 
    @Autowired
    private ObjectMapper objectMapper;
 
    private UserDto userDto;
    private CropDto cropDto;
    private FarmerWrapperDto farmerWrapperDto;
 
    @BeforeEach
    void setUp() {
        userDto = new UserDto();
        userDto.setId(1);
        userDto.setFullName("John Farmer");
        userDto.setEmailId("john.farmer@example.com");
        userDto.setRole("FARMER");
 
        cropDto = new CropDto();
        cropDto.setCropId(101);
        cropDto.setCropName("Wheat");
        cropDto.setCropQuantity("100 kg");
        cropDto.setCropPrice(150.0);
        cropDto.setCropDescription("High-quality wheat");
 
        farmerWrapperDto = new FarmerWrapperDto();
        farmerWrapperDto.setFullname("Farmer John");
        farmerWrapperDto.setAddress("123 Farm Lane");
        farmerWrapperDto.setMobileNumber("1234567890");
    }
 
    @Test
    void testAddUser() throws Exception {
        when(farmerService.addUser(any(UserDto.class))).thenReturn("Farmer added successfully");
 
        mockMvc.perform(post("/farmer/addFarmer")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isOk())
                .andExpect(content().string("Farmer added successfully"));
 
        verify(farmerService, times(1)).addUser(any(UserDto.class));
    }
 
    @Test
    void testDeleteUser() throws Exception {
        when(farmerService.deleteUser(1)).thenReturn("Deleted Successfully");
 
        mockMvc.perform(delete("/farmer/deleteFarmer/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Deleted Successfully"));
 
        verify(farmerService, times(1)).deleteUser(1);
    }
 
    @Test
    void testUpdateUser() throws Exception {
        when(farmerService.updateUser(any(UserDto.class), eq(1))).thenReturn("Updated Successfully");
 
        mockMvc.perform(put("/farmer/updateFarmer/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isOk())
                .andExpect(content().string("Updated Successfully"));
 
        verify(farmerService, times(1)).updateUser(any(UserDto.class), eq(1));
    }
 
    @Test
    void testGetUserById() throws Exception {
        when(farmerService.getUserById(1)).thenReturn(userDto);
 
        mockMvc.perform(get("/farmer/getFarmer/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.fullName").value("John Farmer"))
                .andExpect(jsonPath("$.emailId").value("john.farmer@example.com"));
 
        verify(farmerService, times(1)).getUserById(1);
    }
 
    @Test
    void testAddCrops() throws Exception {
        List<CropDto> crops = Arrays.asList(cropDto);
 
        mockMvc.perform(post("/farmer/1/addCrops")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(crops)))
                .andExpect(status().isOk());
 
        verify(farmerService, times(1)).addCrops(eq(1), anyList());
    }
 
    @Test
    void testGetFarmerWrapperById() throws Exception {
        when(farmerService.getFarmerWrapperById(1)).thenReturn(farmerWrapperDto);
 
        mockMvc.perform(get("/farmer/getFarmerWrapper/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fullname").value("Farmer John"))
                .andExpect(jsonPath("$.address").value("123 Farm Lane"))
                .andExpect(jsonPath("$.mobileNumber").value("1234567890"));
 
        verify(farmerService, times(1)).getFarmerWrapperById(1);
    }
 
    @Test
    void testDeleteCropById() throws Exception {
        doNothing().when(farmerService).deleteCropById(101);
 
        mockMvc.perform(delete("/farmer/deleteCrop/101"))
                .andExpect(status().isOk());
 
        verify(farmerService, times(1)).deleteCropById(101);
    }
}









//package com.example.demo.controller;
//
//import com.example.demo.dto.CropDto;
//import com.example.demo.dto.FarmerWrapperDto;
//import com.example.demo.dto.UserDto;
//import com.example.demo.service.FarmerService;
//import com.fasterxml.jackson.databind.ObjectMapper;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//
//import java.util.Arrays;
//import java.util.List;
//
//import static org.mockito.Mockito.*;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//@WebMvcTest(FarmerController.class)
//public class FarmerControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private FarmerService farmerService;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    private UserDto userDto;
//    private CropDto cropDto;
//    private FarmerWrapperDto farmerWrapperDto;
//
//    @BeforeEach
//    void setUp() {
//        userDto = new UserDto();
//        userDto.setId(1);
//        userDto.setFullName("John Farmer");
//        userDto.setEmailId("john.farmer@example.com");
//        userDto.setRole("FARMER");
//
//        cropDto = new CropDto();
//        cropDto.setCropId(101);
//        cropDto.setCropName("Wheat");
//        cropDto.setCropQuantity("100 kg");
//        cropDto.setCropPrice(150.0);
//        cropDto.setCropDescription("High-quality wheat");
//
//        farmerWrapperDto = new FarmerWrapperDto();
//        farmerWrapperDto.setFullname("Farmer John");
//        farmerWrapperDto.setAddress("123 Farm Lane");
//        farmerWrapperDto.setMobileNumber("1234567890");
//    }
//
//    @Test
//    void testAddUser() throws Exception {
//        when(farmerService.addUser(any(UserDto.class))).thenReturn("Farmer added successfully");
//
//        mockMvc.perform(post("/farmer/addFarmer")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(userDto)))
//                .andExpect(status().isOk())
//                .andExpect(content().string("Farmer added successfully"));
//
//        verify(farmerService, times(1)).addUser(any(UserDto.class));
//    }
//
//    @Test
//    void testDeleteUser() throws Exception {
//        when(farmerService.deleteUser(1)).thenReturn("Deleted Successfully");
//
//        mockMvc.perform(delete("/farmer/deleteFarmer/1"))
//                .andExpect(status().isOk())
//                .andExpect(content().string("Deleted Successfully"));
//
//        verify(farmerService, times(1)).deleteUser(1);
//    }
//
//    @Test
//    void testUpdateUser() throws Exception {
//        when(farmerService.updateUser(any(UserDto.class), eq(1))).thenReturn("Updated Successfully");
//
//        mockMvc.perform(put("/farmer/updateFarmer/1")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(userDto)))
//                .andExpect(status().isOk())
//                .andExpect(content().string("Updated Successfully"));
//
//        verify(farmerService, times(1)).updateUser(any(UserDto.class), eq(1));
//    }
//
//    @Test
//    void testGetUserById() throws Exception {
//        when(farmerService.getUserById(1)).thenReturn(userDto);
//
//        mockMvc.perform(get("/farmer/getFarmer/1"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id").value(1))
//                .andExpect(jsonPath("$.fullName").value("John Farmer"))
//                .andExpect(jsonPath("$.emailId").value("john.farmer@example.com"));
//
//        verify(farmerService, times(1)).getUserById(1);
//    }
//
//    @Test
//    void testAddCrops() throws Exception {
//        List<CropDto> crops = Arrays.asList(cropDto);
//
//        mockMvc.perform(post("/farmer/1/addCrops")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(crops)))
//                .andExpect(status().isOk());
//
//        verify(farmerService, times(1)).addCrops(eq(1), anyList());
//    }
//
//    @Test
//    void testGetFarmerWrapperById() throws Exception {
//        when(farmerService.getFarmerWrapperById(1)).thenReturn(farmerWrapperDto);
//
//        mockMvc.perform(get("/farmer/getFarmerWrapper/1"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.fullname").value("Farmer John"))
//                .andExpect(jsonPath("$.address").value("123 Farm Lane"))
//                .andExpect(jsonPath("$.mobileNumber").value("1234567890"));
//
//        verify(farmerService, times(1)).getFarmerWrapperById(1);
//    }
//
//    @Test
//    void testDeleteCropById() throws Exception {
//        doNothing().when(farmerService).deleteCropById(101);
//
//        mockMvc.perform(delete("/farmer/deleteCrop/101"))
//                .andExpect(status().isOk());
//
//        verify(farmerService, times(1)).deleteCropById(101);
//    }
//}
