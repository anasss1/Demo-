package com.example.Hateoas;

import org.springframework.hateoas.RepresentationModel;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
@Configuration
public class HateoasConfig {
    // Cette configuration permet d'activer le support HATEOAS dans Spring Boot
}
public class OrderResponse extends RepresentationModel<OrderResponse> {
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

        // Ajouter les liens HATEOAS pour les actions possibles
        add(linkTo(methodOn(OrderController.class).getOrderById(orderId)).withSelfRel());
        add(linkTo(methodOn(OrderController.class).cancelOrder(orderId)).withRel("cancel"));
        add(linkTo(methodOn(DeliveryController.class).getAvailableSlots(deliveryMode)).withRel("availableSlots"));
    }

    // Getters and setters
}
