package com.example.demo.controller;

import com.example.demo.controller.AdminController;
import com.example.demo.dto.CropDto;
import com.example.demo.model.User;
import com.example.demo.service.AdminService;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

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


@WebMvcTest(AdminController.class)
class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AdminService adminService;

    @Autowired
    private ObjectMapper objectMapper;

    private User user;
    private CropDto cropDto;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1);
        user.setFullName("John Doe");
        user.setEmailId("john.doe@example.com");
        user.setPassword("password123");
        user.setMobileNumber("1234567890");
        user.setGender("Male");
        user.setRole("ADMIN");
        user.setAddress("123 Main Street, City, Country");

        cropDto = new CropDto();
        cropDto.setCropId(101);
        cropDto.setCropName("Wheat");
        cropDto.setCropQuantity("100 kg");
        cropDto.setCropPrice(150.0);
        cropDto.setCropDescription("High-quality wheat");
    }

    @Test
    void testDeleteUser() throws Exception {
        when(adminService.deleteUser(1)).thenReturn("Delete Succesfully");

        mockMvc.perform(delete("/admin/deleteUser/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Delete Succesfully"));

        verify(adminService, times(1)).deleteUser(1);
    }

    @Test
    void testUpdateUser() throws Exception {
        when(adminService.updateUser(any(User.class), eq(1))).thenReturn("Updated Succesfully");

        mockMvc.perform(put("/admin/updateUser/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk())
                .andExpect(content().string("Updated Succesfully"));

        verify(adminService, times(1)).updateUser(any(User.class), eq(1));
    }

    @Test
    void testGetAllUsers() throws Exception {
        List<User> userList = Arrays.asList(user);
        when(adminService.getUsers()).thenReturn(userList);

        mockMvc.perform(get("/admin/getAllUsers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].fullName").value("John Doe"));

        verify(adminService, times(1)).getUsers();
    }

    @Test
    void testGetUserById() throws Exception {
        when(adminService.getUserById(1)).thenReturn(user);

        mockMvc.perform(get("/admin/getUser/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fullName").value("John Doe"));

        verify(adminService, times(1)).getUserById(1);
    }

    @Test
    void testGetAllCrops() throws Exception {
        List<CropDto> cropList = Arrays.asList(cropDto);
        when(adminService.getAllCrops()).thenReturn(cropList);

        mockMvc.perform(get("/admin/getAllCrops"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].cropName").value("Wheat"));

verify(adminService, times(1)).getAllCrops();
}

}
