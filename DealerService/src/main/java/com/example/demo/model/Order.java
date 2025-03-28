package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;
import java.util.Date;

@Entity
@Data
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int orderId;

    @Column(name = "dealer_id", nullable = false)
    private int dealerId;

    @Column(name = "crop_id", nullable = false)
    private int cropId;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    @Column(name = "total_price")
    private double totalPrice;

    @Column(name = "order_date")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Kolkata")  // ✅ Added JsonFormat
    private Date orderDate;
}
