package com.example.demo.controller;

import com.example.demo.DealerServiceApplication;
// Import your main application class
import com.example.demo.dto.CropDto;
import com.example.demo.dto.FarmerWrapperDto;
import com.example.demo.dto.UserDto;
import com.example.demo.service.DealerService;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = DealerController.class)
@ContextConfiguration(classes = DealerServiceApplication.class) // Explicitly specify configuration
public class DealerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DealerService dealerService;

    @Autowired
    private ObjectMapper objectMapper;

    private UserDto userDto;
    private CropDto cropDto;
    private FarmerWrapperDto farmerWrapperDto;

    @BeforeEach
    void setUp() {
        userDto = new UserDto();
        userDto.setId(1);
        userDto.setFullName("John Dealer");
        userDto.setRole("DEALER");
        userDto.setEmailId("john.dealer@example.com");

        cropDto = new CropDto();
        cropDto.setCropId(101);
        cropDto.setCropName("Wheat");
        cropDto.setCropQuantity("100 kg");
        cropDto.setCropPrice(150.0);
        cropDto.setCropDescription("High-quality wheat");

        farmerWrapperDto = new FarmerWrapperDto();
        farmerWrapperDto.setFullname("Farmer John");
        farmerWrapperDto.setAddress("123 Farm Street");
        farmerWrapperDto.setMobileNumber("1234567890");
    }

    @Test
    void testDeleteUser() throws Exception {
        when(dealerService.deleteUser(1)).thenReturn("Dealer deleted successfully");

        mockMvc.perform(delete("/dealer/deleteDealer/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Dealer deleted successfully"));

        verify(dealerService, times(1)).deleteUser(1);
    }
}