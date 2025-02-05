package com.example;

@RestController
@RequestMapping("/delivery")
public class DeliveryController {

    @GetMapping("/modes")
    public ResponseEntity<List<String>> getDeliveryModes() {
        List<String> modes = Arrays.asList("DRIVE", "DELIVERY", "DELIVERY_TODAY", "DELIVERY_ASAP");
        return ResponseEntity.ok(modes);
    }
}
@GetMapping("/{mode}/slots")
public ResponseEntity<List<String>> getAvailableSlots(@PathVariable String mode) {
    // Logique pour obtenir les créneaux horaires disponibles en fonction du mode
    List<String> availableSlots = deliveryService.getAvailableSlots(mode);
    return ResponseEntity.ok(availableSlots);
}
@DeleteMapping("/{mode}/slots/{slot}/cancel")
public ResponseEntity<String> cancelReservation(
        @PathVariable String mode,
        @PathVariable String slot,
        @RequestBody ReservationRequest reservationRequest) {

    boolean success = deliveryService.cancelReservation(mode, slot, reservationRequest);
    
    if (success) {
        return ResponseEntity.ok("Reservation cancelled.");
    } else {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Cancellation failed.");
    }
}

