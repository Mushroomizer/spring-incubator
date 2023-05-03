package entelect.training.incubator.spring.booking.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
public class Booking {

    public Booking(Integer customer_id, Integer flight_id) {
        this.customer_id = customer_id;
        this.flight_id = flight_id;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer reference_number;
    private Integer customer_id;
    private Integer flight_id;
}
