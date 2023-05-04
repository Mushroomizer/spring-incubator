package entelect.training.incubator.spring.booking.service;

import entelect.training.incubator.spring.booking.model.Booking;
import entelect.training.incubator.spring.booking.repository.BookingRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingService {

    private final BookingRepository _bookingRepository;

    public BookingService(BookingRepository _bookingRepository) {
        this._bookingRepository = _bookingRepository;
    }

    public Booking createBooking(Booking booking) {
        return _bookingRepository.save(booking);
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
