package com.example.demo.dto;



import lombok.Data;

@Data
public class InvoiceDto {
    private String invoiceId;
    private String transactionId;
    private int dealerId;
    private int cropId;
    private double amount;
    private String invoiceDate;

    // Add these fields if required
    private String cropName;  
    private int quantity;
    private String dealerName;
    private String dealerContact;
    private String farmerName;
    private String farmerContact;
    private String paymentStatus;
}
