package entelect.training.incubator.spring.booking.repository;
import entelect.training.incubator.spring.booking.model.Booking;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepository extends CrudRepository<Booking,Integer>{

    Optional<Booking> findByReference_number(Integer referenceNumber);
    Optional<List<Booking>> findByCustomerId(Integer customerId);
}
