package entelect.training.incubator.spring.booking.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import java.io.Serializable;

@Service
public class QueueServiceImpl implements QueueService {

    private final JmsTemplate _jmsTemplate;
    @Value("${queue.service.destination}")
    private String destinationQueue;

    public QueueServiceImpl(JmsTemplate _jmsTemplate) {
        this._jmsTemplate = _jmsTemplate;
    }

    @Override
    public void sendMessage(String message) {
        _jmsTemplate.send(destinationQueue, session -> session.createTextMessage(message));
    }
}
