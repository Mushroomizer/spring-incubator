package entelect.training.incubator.spring.notification.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class NotificationMessage{
    private String phoneNumber;
    private String message;
}
