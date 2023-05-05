package entelect.training.incubator.spring.booking.controller;


import entelect.training.incubator.spring.booking.model.Booking;
import entelect.training.incubator.spring.booking.model.CreateBookingRequest;
import entelect.training.incubator.spring.booking.model.Customer;
import entelect.training.incubator.spring.booking.model.Flight;
import entelect.training.incubator.spring.booking.service.BookingService;
import entelect.training.incubator.spring.booking.service.CustomerClientService;
import entelect.training.incubator.spring.booking.service.FlightClientService;
import entelect.training.incubator.spring.booking.rewards.client.RewardsClient;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RestController
@SecurityRequirement(name = "basicAuth")
public class BookingController {

    private final Logger LOGGER = LoggerFactory.getLogger(BookingController.class);
    private final BookingService _bookingService;
    private final CustomerClientService _customerClientService;
    private final FlightClientService _flightClientService;
    private final RewardsClient _rewardsClient;

    public BookingController(BookingService _bookingService, CustomerClientService _customerClientService, FlightClientService _flightClientService, RewardsClient _rewardsClient) {
        this._bookingService = _bookingService;
        this._customerClientService = _customerClientService;
        this._flightClientService = _flightClientService;
        this._rewardsClient = _rewardsClient;
    }

    @PostMapping("/bookings")
    @Operation(summary = "Make Booking")
    public ResponseEntity<?> makeBooking(@RequestBody CreateBookingRequest request) {

        Customer customer = _customerClientService.getCustomerById(request.customerId);
        if (customer == null || !customer.getId().equals(request.customerId)) {
            LOGGER.info("No customer found with id={}", request.customerId);
            return new ResponseEntity<>("Invalid Customer id", HttpStatus.BAD_REQUEST);
        }

        Flight flight = _flightClientService.getFlightById(request.flightId);
        if (flight == null || !flight.getId().equals(request.flightId)) {
            LOGGER.info("No flight found with id={}", request.flightId);
            return new ResponseEntity<>("Invalid Flight id", HttpStatus.BAD_REQUEST);
        }

        Booking savedBooking = _bookingService.createBooking(new Booking(request.customerId, request.flightId),customer,flight);
        LOGGER.info("Booking created with reference number={}", savedBooking.getId());

        _rewardsClient.updateRewards(customer.getPassportNumber(), BigDecimal.ONE);

        return new ResponseEntity<>(savedBooking, HttpStatus.OK);
    }

    @GetMapping("/bookings/{id}")
    @Operation(summary = "Get Booking by id")
    public ResponseEntity<?> getBookingById(@PathVariable Integer id) {
        Booking booking = _bookingService.getBookingById(id);

        if (booking != null) {
            LOGGER.info("Booking found with reference number={}", booking.getReferenceNumber());
            return new ResponseEntity<>(booking, HttpStatus.OK);
        }

        LOGGER.info("No booking found with id={}", id);
        return new ResponseEntity<>("No booking found", HttpStatus.OK);
    }

    @PostMapping(value = "/bookings/search", params = "customerId")
    @Operation(summary = "Search booking by customer id")
    public ResponseEntity<?> searchBookingByCustomerId(@RequestParam(value = "customerId", required = false) Integer customerId) {
        List<Booking> bookings = _bookingService.getBookingsByCustomerId(customerId);
        LOGGER.info("{} Bookings found", bookings.size());
        return new ResponseEntity<>(bookings, HttpStatus.OK);
    }

    @PostMapping(value = "/bookings/search", params = "referenceNumber")
    @Operation(summary = "Search booking by reference number")
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
