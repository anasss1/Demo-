package com.example.Hateoas;
@RestController
@RequestMapping("/delivery")
public class DeliveryController {

    @GetMapping("/{mode}/slots")
    public ResponseEntity<List<String>> getAvailableSlots(@PathVariable String mode) {
        // Logique pour obtenir les créneaux horaires disponibles en fonction du mode
        List<String> availableSlots = deliveryService.getAvailableSlots(mode);

        // Enrichir la réponse avec des liens HATEOAS
        SlotAvailabilityResponse response = new SlotAvailabilityResponse(true);
        response.add(linkTo(methodOn(DeliveryController.class).getAvailableSlots(mode)).withSelfRel());

        return ResponseEntity.ok(availableSlots);
    }
}
public class ReservationResponse extends RepresentationModel<ReservationResponse> {
    private String orderId;
    private String deliverySlot;
    private String status;

    public ReservationResponse(String orderId, String deliverySlot, String status) {
        this.orderId = orderId;
        this.deliverySlot = deliverySlot;
        this.status = status;

        // Ajouter des liens HATEOAS
        add(linkTo(methodOn(OrderController.class).getOrderById(orderId)).withRel("orderDetails"));
        add(linkTo(methodOn(DeliveryController.class).getAvailableSlots("DRIVE")).withRel("availableSlots"));
    }

    // Getters and setters
}
@PostMapping("/{mode}/slots/{slot}/reserve")
public ResponseEntity<ReservationResponse> reserveSlot(@PathVariable String mode, 
                                                       @PathVariable String slot,
                                                       @RequestBody ReservationRequest reservationRequest) {
    // Logique de réservation du créneau horaire
    ReservationResponse response = new ReservationResponse("12345", slot, "Reserved");

    // Enrichir la réponse avec des liens HATEOAS
    response.add(linkTo(methodOn(DeliveryController.class).getAvailableSlots(mode)).withRel("availableSlots"));
    response.add(linkTo(methodOn(OrderController.class).getOrderById("12345")).withRel("orderDetails"));

    return ResponseEntity.status(HttpStatus.CREATED).body(response);
}
