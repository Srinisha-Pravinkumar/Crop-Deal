package com.example.demo.dto;


import lombok.Data;

@Data
public class PaymentDto {
    private String transactionId;
    private int dealerId;
    private int cropId;
    private double amount;
    private String paymentStatus;
    private String paymentDate;
}
