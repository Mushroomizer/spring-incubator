package entelect.training.incubator.spring.booking.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GeneratorType;

import javax.persistence.*;
import java.util.Random;

@Data
@NoArgsConstructor
@Entity
public class Booking {

    public Booking(Integer customer_id, Integer flight_id) {
        this.customerId = customer_id;
        this.flightId = flight_id;
        this.referenceNumber = String.format("%s%s%s", customer_id, flight_id, new Random().nextInt(10000));
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "reference_number")
    private String referenceNumber;

    @Column(name = "customer_id")
    private Integer customerId;

    @Column(name = "flight_id")
    private Integer flightId;
}
