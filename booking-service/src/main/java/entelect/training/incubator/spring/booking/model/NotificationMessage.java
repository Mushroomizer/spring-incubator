package entelect.training.incubator.spring.booking.model;

import lombok.AllArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
public class NotificationMessage{
    private String phoneNumber;
    private String message;
}
