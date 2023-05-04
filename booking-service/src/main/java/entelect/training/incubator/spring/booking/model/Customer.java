package entelect.training.incubator.spring.booking.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Customer {

    private Integer Id;

    private String username;

    private String firstName;

    private String lastName;

    private String passportNumber;

    private String email;

    private String phoneNumber;


}
