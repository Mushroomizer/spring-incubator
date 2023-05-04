package entelect.training.incubator.spring.booking.controller;


import entelect.training.incubator.spring.booking.model.Booking;
import entelect.training.incubator.spring.booking.model.CreateBookingRequest;
import entelect.training.incubator.spring.booking.rewards.client.gen.CaptureRewardsResponse;
import entelect.training.incubator.spring.booking.rewards.client.gen.RewardsBalanceResponse;
import entelect.training.incubator.spring.booking.service.BookingService;
import entelect.training.incubator.spring.booking.service.CustomerClientService;
import entelect.training.incubator.spring.booking.service.FlightClientService;
import entelect.training.incubator.spring.booking.rewards.client.RewardsClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RestController
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
    public ResponseEntity<?> makeBooking(@RequestBody CreateBookingRequest request) {
        if (!_customerClientService.isCustomerIdValid(request.customerId)) {
            LOGGER.info("No customer found with id={}", request.customerId);
            return new ResponseEntity<>("Invalid Customer id", HttpStatus.BAD_REQUEST);
        }

        if (!_flightClientService.isFlightIdValid(request.flightId)) {
            LOGGER.info("No flight found with id={}", request.flightId);
            return new ResponseEntity<>("Invalid Flight id", HttpStatus.BAD_REQUEST);
        }

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
        return new ResponseEntity<>("No booking found", HttpStatus.OK);
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

    @PostMapping(value = "test")
    public ResponseEntity<?> test() {
        try{
            CaptureRewardsResponse captureRewardsResponse = _rewardsClient.updateRewards("1234", BigDecimal.ONE);
            return new ResponseEntity<>(captureRewardsResponse.getBalance(), HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
        }


        return ResponseEntity.ok().build();
    }

}
