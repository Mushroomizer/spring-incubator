package entelect.training.incubator.spring.notification.service;

import com.google.gson.Gson;
import entelect.training.incubator.spring.notification.model.NotificationMessage;
import entelect.training.incubator.spring.notification.sms.client.SmsClient;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;
import java.util.Map;

@Service
public class NotificationQueueListener {
    private final Gson gson = new Gson();
    private final SmsClient _smsClient;

    public NotificationQueueListener(SmsClient _smsClient) {
        this._smsClient = _smsClient;
    }

    @JmsListener(destination = "notification.queue")
    public void receiveMessage(final Message jsonMessage) throws JMSException {
        System.out.println("Received message " + jsonMessage);
        if (jsonMessage instanceof TextMessage) {
            TextMessage textMessage = (TextMessage) jsonMessage;
            NotificationMessage notificationMessage = gson.fromJson(textMessage.getText(), NotificationMessage.class);
            _smsClient.sendSms(notificationMessage.getPhoneNumber(), notificationMessage.getMessage());
        }
    }
}
