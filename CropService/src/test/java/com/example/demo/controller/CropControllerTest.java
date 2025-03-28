package com.example.demo.controller;



import com.example.demo.controller.CropController;
import com.example.demo.dto.CropDto;
import com.example.demo.model.CropModel;
import com.example.demo.service.CropService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

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

@WebMvcTest(CropController.class)
public class CropControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CropService cropService;

    @Autowired
    private ObjectMapper objectMapper;

    private CropModel cropModel;
    private CropDto cropDto;

    @BeforeEach
    void setUp() {
        cropModel = new CropModel();
        cropModel.setCropId(101);
        cropModel.setCropName("Wheat");
        cropModel.setCropQuantity("100 kg");
        cropModel.setCropPrice(150.0);
        cropModel.setCropDescription("High-quality wheat");

        cropDto = new CropDto();
        cropDto.setCropName("Wheat");
        cropDto.setCropQuantity("100 kg");
        //scropDto.setId(1);
    }

    @Test
    void testGetAllCrops() throws Exception {
        List<CropModel> crops = Arrays.asList(cropModel);
        when(cropService.getCrops()).thenReturn(crops);

        mockMvc.perform(get("/crop/getAllcrops"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].cropName").value("Wheat"))
                .andExpect(jsonPath("$[0].cropQuantity").value("100 kg"))
                .andExpect(jsonPath("$[0].cropPrice").value(150.0))
                .andExpect(jsonPath("$[0].cropDescription").value("High-quality wheat"));

        verify(cropService, times(1)).getCrops();
    }

    @Test
    void testGetCropsByFarmerId() throws Exception {
        List<CropModel> crops = Arrays.asList(cropModel);
        when(cropService.getCropsById(1)).thenReturn(crops);

        mockMvc.perform(get("/crop/getCrops/farmer/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].cropName").value("Wheat"));

        verify(cropService, times(1)).getCropsById(1);
    }

    @Test
    void testAddCrops() throws Exception {
        List<CropDto> crops = Arrays.asList(cropDto);

        mockMvc.perform(post("/crop/farmer/1/addCrops")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(crops)))
                .andExpect(status().isOk());

        verify(cropService, times(1)).addCrops(eq(1), any());
    }

    @Test
    void testDeleteByIdAndCropId() throws Exception {
        doNothing().when(cropService).deleteByIdAndCropId(1, 101);

        mockMvc.perform(delete("/crop/farmer/1/deleteCrop/101"))
                .andExpect(status().isOk());

        verify(cropService, times(1)).deleteByIdAndCropId(1, 101);
    }

    @Test
    void testDeleteCropById() throws Exception {
        doNothing().when(cropService).deleteCropById(101);

        mockMvc.perform(delete("/crop/deleteCropById/101"))
                .andExpect(status().isOk());

        verify(cropService, times(1)).deleteCropById(101);
    }
}
