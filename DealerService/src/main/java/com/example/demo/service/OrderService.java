package com.example.demo.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.apache.catalina.startup.UserDatabase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.CropDto;
import com.example.demo.dto.FarmerWrapperDto;
import com.example.demo.dto.InvoiceDto;
import com.example.demo.dto.Orderdto;
import com.example.demo.dto.PaymentDto;
import com.example.demo.dto.UserDto;
import com.example.demo.feign.CropFeign;
import com.example.demo.feign.DealerFeign;
import com.example.demo.feign.FarmerFeign;
import com.example.demo.model.CropModel;
import com.example.demo.model.Order;
import com.example.demo.repo.CropRepository;
import com.example.demo.repo.OrderRepository;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CropFeign cropFeign;

    @Autowired
    private CropRepository cropRepository;

    // Place an order
    @Autowired
    private DealerFeign dealerFeign;
    
    @Autowired
    private FarmerFeign farmerFeign;
 // ✅ Place order and initiate payment
 // ✅ Place order and initiate payment
    public String placeOrder(Orderdto orderDto) {
        // ✅ Fetch user details from AdminService to validate role
        UserDto userDto = dealerFeign.getUserById(orderDto.getDealerId());

        if (userDto == null || !userDto.getRole().equalsIgnoreCase("DEALER")) {
            return "❌ Invalid User! Only DEALER can place orders.";
        }

        Optional<CropModel> cropOptional = cropRepository.findById(orderDto.getCropId());
        if (cropOptional.isPresent()) {
            CropModel crop = cropOptional.get();

            // ✅ Extract numeric value from cropQuantity
            String cropQuantity = crop.getCropQuantity().replaceAll("[^0-9]", "");
            int availableQuantity;

            try {
                availableQuantity = Integer.parseInt(cropQuantity);
            } catch (NumberFormatException e) {
                return "❌ Invalid crop quantity format in the database!";
            }

            // ✅ Check if the quantity is sufficient
            if (availableQuantity >= orderDto.getQuantity()) {
                // ✅ Create and save order
                Order order = new Order();
                order.setDealerId(orderDto.getDealerId());
                order.setCropId(orderDto.getCropId());
                order.setQuantity(orderDto.getQuantity());
                order.setTotalPrice(orderDto.getQuantity() * crop.getCropPrice());
                order.setOrderDate(new Date());

                orderRepository.save(order);

                // ✅ Update crop quantity after order
                int newQuantity = availableQuantity - orderDto.getQuantity();
                crop.setCropQuantity(String.valueOf(newQuantity));
                cropRepository.save(crop);

                // ✅ Process Payment After Order is Placed
                PaymentDto paymentDto = new PaymentDto();
                paymentDto.setDealerId(orderDto.getDealerId());
                paymentDto.setCropId(orderDto.getCropId());
                paymentDto.setAmount(orderDto.getQuantity() * crop.getCropPrice());
                paymentDto.setPaymentStatus("Success");
                paymentDto.setTransactionId("TXN" + System.currentTimeMillis());
                paymentDto.setPaymentDate(new Date().toString());

                // ✅ Generate Invoice
                InvoiceDto invoiceDto = generateInvoice(order, paymentDto);

                // ✅ Final Success Message with All Details
                String message = "✅ Order placed successfully! 🎉\n" +
                        "➡️ Transaction ID: " + invoiceDto.getTransactionId() + "\n" +
                        "🧾 Invoice ID: " + invoiceDto.getInvoiceId() + "\n" +
                        "🌱 Crop Name: " + invoiceDto.getCropName() + "\n" +
                        "📦 Quantity: " + invoiceDto.getQuantity() + "\n" +
                        "💰 Total Price: ₹" + invoiceDto.getAmount() + "\n" +
                        "👨‍🌾 Farmer Name: " + invoiceDto.getFarmerName() + "\n" +
                        "📞 Farmer Contact: " + invoiceDto.getFarmerContact() + "\n" +
                        "🤝 Dealer Name: " + invoiceDto.getDealerName() + "\n" +
                        "📞 Dealer Contact: " + invoiceDto.getDealerContact() + "\n" +
                        "💳 Payment Status: " + invoiceDto.getPaymentStatus() + "\n" +
                        "📅 Invoice Date: " + invoiceDto.getInvoiceDate();

                if (newQuantity == 0) {
                    message += "\n⚠️ Crop is now Out of Stock.";
                }

                return message;
            } else {
                return "❌ Insufficient crop quantity. Available quantity: " + availableQuantity;
            }
        } else {
            return "❌ Crop not found!";
        }
    }


    // ✅ Dummy Payment Processing
    public PaymentDto processPayment(Order order) {
        PaymentDto paymentDto = new PaymentDto();

        // Generate dummy Transaction ID
        String transactionId = UUID.randomUUID().toString();

        paymentDto.setTransactionId(transactionId);
        paymentDto.setDealerId(order.getDealerId());
        paymentDto.setCropId(order.getCropId());
        paymentDto.setAmount(order.getTotalPrice());
        paymentDto.setPaymentDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));

        // Simulate Payment Success/Failure
        if (order.getTotalPrice() > 0) {
            paymentDto.setPaymentStatus("SUCCESS");
        } else {
            paymentDto.setPaymentStatus("FAILED");
        }

        return paymentDto;
    }

    // ✅ Generate Invoice After Payment
 // ✅ Generate Invoice After Payment
    public InvoiceDto generateInvoice(Order order, PaymentDto paymentDto) {
        InvoiceDto invoice = new InvoiceDto();

        // Fetch dealer details
        UserDto dealer = dealerFeign.getUserById(order.getDealerId());
        Optional<CropModel> cropOptional = cropRepository.findById(order.getCropId());
        CropDto cropDto = cropFeign.getCrops().stream()
                .filter(crop -> crop.getCropId() == order.getCropId())
                .findFirst()
                .orElse(null);

        // ✅ Fetch farmer details using the correct ID
        FarmerWrapperDto farmer = cropOptional.isPresent() ?
                farmerFeign.getFarmerWrapperById(cropOptional.get().getId()) : null;


        invoice.setInvoiceId("INV" + System.currentTimeMillis());
        invoice.setTransactionId(paymentDto.getTransactionId());
        invoice.setDealerId(order.getDealerId());
        invoice.setCropId(order.getCropId());
        invoice.setAmount(order.getTotalPrice());
        invoice.setInvoiceDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        invoice.setPaymentStatus(paymentDto.getPaymentStatus());

        // ✅ Add additional details if available
        invoice.setCropName(cropDto != null ? cropDto.getCropName() : "Unknown Crop");
        invoice.setQuantity(order.getQuantity());
        invoice.setDealerName(dealer != null ? dealer.getFullName() : "Unknown Dealer");
        invoice.setDealerContact(dealer != null ? dealer.getMobileNumber() : "Unknown Contact");

        // ✅ Correctly fetch farmer name and contact
        invoice.setFarmerName(farmer != null ? farmer.getFullname() : "Unknown Farmer");
        invoice.setFarmerContact(farmer != null ? farmer.getMobileNumber() : "Unknown Contact");

        return invoice;
    }



    // ✅ Fetch all orders
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }
}