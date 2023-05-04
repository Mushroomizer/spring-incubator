package entelect.training.incubator.spring.booking.controller;


import entelect.training.incubator.spring.booking.model.Booking;
import entelect.training.incubator.spring.booking.model.CreateBookingRequest;
import entelect.training.incubator.spring.booking.service.BookingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class BookingController {

    private final Logger LOGGER = LoggerFactory.getLogger(BookingController.class);

    private static BookingService _bookingService;

    public BookingController(BookingService bookingService) {

        _bookingService = bookingService;
    }


    @PostMapping("/bookings")
    public ResponseEntity<?> makeBooking(@RequestBody CreateBookingRequest request) {
        // TODO: Check if customer exists
        // TODO: Check if flight exists
        Booking savedBooking = _bookingService.createBooking(new Booking(request.customerId, request.flightId));
        LOGGER.info("Booking created with reference number={}", savedBooking.getId());
        return new ResponseEntity<>(savedBooking, HttpStatus.OK);
    }

    @GetMapping("/bookings/{id}")
    public ResponseEntity<?> getBookingById(@PathVariable Integer id) {
        Booking booking = _bookingService.getBookingById(id);
        if (booking != null) {
            LOGGER.info("Booking found with reference number={}", booking.getReferenceNumber());
            return new ResponseEntity<>(booking, HttpStatus.OK);
        }
        LOGGER.info("No booking found with id={}", id);
        return ResponseEntity.notFound().build();
    }

    @PostMapping(value = "/bookings/search", params = "customerId")
    public ResponseEntity<?> searchBookingByCustomerId(@RequestParam(value = "customerId", required = false) Integer customerId) {
        List<Booking> bookings = _bookingService.getBookingsByCustomerId(customerId);
        LOGGER.info("{} Bookings found", bookings.size());
        return new ResponseEntity<>(bookings, HttpStatus.OK);
    }

    @PostMapping(value = "/bookings/search", params = "referenceNumber")
    public ResponseEntity<?> searchBookingByReferenceNumber(@RequestParam(value = "referenceNumber", required = false) String referenceNumber) {
        List<Booking> bookings = new ArrayList<>();
        Booking booking = _bookingService.getBookingByReferenceNumber(referenceNumber);
        if (booking != null) {
            bookings.add(booking);
        }
        LOGGER.info("{} Bookings found", bookings.size());
        return new ResponseEntity<>(bookings, HttpStatus.OK);
    }

}
