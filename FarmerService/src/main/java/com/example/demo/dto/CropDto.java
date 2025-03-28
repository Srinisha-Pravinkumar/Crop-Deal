package com.example.demo.dto;

import lombok.Data;

@Data
public class CropDto {
	 private int cropId;
	 private String cropName;
	 private String cropQuantity;
	 private double cropPrice;
	
	private String cropDescription;
}
