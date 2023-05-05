package entelect.training.incubator.spring.booking.service;

import entelect.training.incubator.spring.booking.model.Customer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.client.support.BasicAuthenticationInterceptor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CustomerClientService {

    @Value("${customer.service.url}")
    private String customerServiceUrl;

    @Value("${customer.service.username}")
    private String serviceUsername;

    @Value("${customer.service.password}")
    private String servicePassword;

    public Customer getCustomerById(Integer customerId){
        try {
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getInterceptors().add(new BasicAuthenticationInterceptor(serviceUsername, servicePassword));
            return restTemplate.getForObject(String.format("%s/customers/%d", customerServiceUrl, customerId), Customer.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
