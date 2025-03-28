package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.demo.dto.Orderdto;
import com.example.demo.model.Order;
import com.example.demo.service.OrderService;

import java.util.List;

@RestController
@RequestMapping("dealer/order")
@CrossOrigin(origins = "*")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/placeOrder")
    public String placeOrder(@RequestBody Orderdto orderDto) {
        return orderService.placeOrder(orderDto);
    }

    @GetMapping("/getAllOrders")
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }

}
