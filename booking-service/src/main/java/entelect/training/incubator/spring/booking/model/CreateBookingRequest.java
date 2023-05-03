package entelect.training.incubator.spring.booking.model;

public class CreateBookingRequest {
    public final Integer customerId;
    public final Integer flightId;

    public CreateBookingRequest(Integer customerId, Integer flightId) {
        this.customerId = customerId;
        this.flightId = flightId;
    }
}
