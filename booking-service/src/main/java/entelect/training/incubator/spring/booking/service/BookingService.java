package entelect.training.incubator.spring.booking.service;

import com.google.gson.Gson;
import entelect.training.incubator.spring.booking.model.Booking;
import entelect.training.incubator.spring.booking.model.Customer;
import entelect.training.incubator.spring.booking.model.Flight;
import entelect.training.incubator.spring.booking.model.NotificationMessage;
import entelect.training.incubator.spring.booking.repository.BookingRepository;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class BookingService {
    private final BookingRepository _bookingRepository;
    private final QueueService _queueService;
    private final Gson gson = new Gson();

    public BookingService(BookingRepository _bookingRepository, QueueService queueService) {
        this._bookingRepository = _bookingRepository;
        this._queueService = queueService;
    }

    public Booking createBooking(Booking booking, Customer customer, Flight flight) {
        Booking savedBooking = _bookingRepository.save(booking);

        NotificationMessage notificationToSend = new NotificationMessage(customer.getPhoneNumber(),
                String.format("Molo Air: Confirming flight %s booked for %s %s on %s.",
                        flight.getFlightNumber(),
                        customer.getFirstName(),
                        customer.getLastName(),
                        flight.getDepartureTime().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))
                )
        );


        _queueService.sendMessage(gson.toJson(notificationToSend));
        return savedBooking;
    }

    public Booking getBookingById(Integer id) {
        return _bookingRepository.findById(id).orElse(null);
    }

    public Booking getBookingByReferenceNumber(String referenceNumber) {
        return _bookingRepository.findByReferenceNumber(referenceNumber).orElse(null);
    }

    public List<Booking> getBookingsByCustomerId(Integer customerId) {
        return _bookingRepository.findBookingByCustomerId(customerId).orElse(null);
    }
}
