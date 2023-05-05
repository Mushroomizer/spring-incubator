package entelect.training.incubator.spring.booking.service;

import entelect.training.incubator.spring.booking.model.Flight;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.client.support.BasicAuthenticationInterceptor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class FlightClientService {
    @Value("${flight.service.url}")
    private String flightServiceUrl;

    @Value("${flight.service.username}")
    private String serviceUsername;

    @Value("${flight.service.password}")
    private String servicePassword;

    public Flight getFlightById(Integer flightId){
        try {
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getInterceptors().add(new BasicAuthenticationInterceptor(serviceUsername, servicePassword
            ));
            return restTemplate.getForObject(String.format("%s/flights/%d", flightServiceUrl, flightId), Flight.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
