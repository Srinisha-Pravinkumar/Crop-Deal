package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CropDto {
    private Integer cropId;
    @NotBlank(message = "Crop name is required")
    private String cropName;

    @NotBlank(message = "Crop quantity is required")
    private String cropQuantity;

    @NotNull(message = "Crop price is required")
    private Double cropPrice;

    @NotBlank(message = "Crop description is required")
    private String cropDescription;
}
