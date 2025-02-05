package com.example;

public class ResponseDTO {
    public class OrderResponse {
        private String orderId;
        private String customerId;
        private String deliveryMode;
        private String deliverySlot;
        private String status;
    
        public OrderResponse(String customerId, String deliveryMode, String deliverySlot) {
            this.orderId = UUID.randomUUID().toString();
            this.customerId = customerId;
            this.deliveryMode = deliveryMode;
            this.deliverySlot = deliverySlot;
            this.status = "Pending";
        }
    }
    
}
