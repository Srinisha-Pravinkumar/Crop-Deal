package com.example.demo.service;


import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import com.example.demo.dto.CropDto;

@Service
public class DealerNotificationService {

    @RabbitListener(queues = "crop_notification_queue")
    public void receiveNotification(CropDto cropDto) {
        System.out.println("Notification Received for Crop: " + cropDto.getCropName());
        // Add logic to notify dealers
        notifyDealers(cropDto);
    }

    private void notifyDealers(CropDto cropDto) {
        System.out.println("Sending notification to dealers for crop: " + cropDto.getCropName());
        // Add logic to notify all subscribed dealers
    }
}
