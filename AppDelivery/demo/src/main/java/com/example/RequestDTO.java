package com.example;

public class RequestDTO {
    public class OrderRequest {
        private String customerId;
        private String deliveryMode;
        private String deliverySlot;
        private List<OrderItem> items;
    }
    
}
