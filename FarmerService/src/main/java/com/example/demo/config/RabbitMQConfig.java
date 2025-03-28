package com.example.demo.config;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Bean
    public Queue cropQueue() {
        return new Queue("crop_notification_queue");
    }

    @Bean
    public DirectExchange cropExchange() {
        return new DirectExchange("crop_exchange");
    }

    @Bean
    public Binding binding(Queue cropQueue, DirectExchange cropExchange) {
        return BindingBuilder.bind(cropQueue).to(cropExchange).with("crop_routing_key");
    }
}
