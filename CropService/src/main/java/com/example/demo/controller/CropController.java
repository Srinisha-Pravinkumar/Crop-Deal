package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.demo.dto.CropDto;
import com.example.demo.model.CropModel;
import com.example.demo.service.CropService;

import jakarta.transaction.Transactional;

@RestController
@RequestMapping("crop")
@CrossOrigin(origins = "*")
public class CropController {

    @Autowired
    private CropService cropService;

    @GetMapping("getAllcrops")
    public List<CropModel> getCrops() {
        return cropService.getCrops();
    }

    @GetMapping("getCrops/farmer/{id}")
    public List<CropModel> getCropsByFarmerId(@PathVariable int id) {
        return cropService.getCropsById(id);
    }

    @Transactional
    @PostMapping("farmer/{id}/addCrops")
    public void addCrops(@PathVariable int id, @RequestBody List<CropDto> crops) {
        cropService.addCrops(id, crops);
    }

    @DeleteMapping("farmer/{farmerId}/deleteCrop/{cropId}")
    public void deleteByIdAndCropId(@PathVariable Integer farmerId, @PathVariable Integer cropId) {
        cropService.deleteByIdAndCropId(farmerId, cropId);
    }

    @DeleteMapping("deleteCropById/{id}")
    public void deleteCropById(@PathVariable int id) {
        cropService.deleteCropById(id);
    }
    
}
