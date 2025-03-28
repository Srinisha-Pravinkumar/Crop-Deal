package com.example.demo.dto;


import lombok.Data;

@Data
public class CropWithFarmerIdDto {
	private int farmerId; // ✅ Correct field name

//	List<> cropDetails;
//	private String cropName;
//	private String cropQuantity;
//	private double cropPrice;
//	private String cropDescription;
}
