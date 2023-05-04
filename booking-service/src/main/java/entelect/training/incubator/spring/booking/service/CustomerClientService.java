package entelect.training.incubator.spring.booking.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.client.support.BasicAuthenticationInterceptor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CustomerClientService {

    @Value("${customer.service.url}")
    private String customerServiceUrl;

    public boolean isCustomerIdValid(Integer customerId) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getInterceptors().add(new BasicAuthenticationInterceptor("admin", "is_a_lie"));
            String result = restTemplate.getForObject(String.format("%s/customers/%d", customerServiceUrl, customerId), String.class);
            if (result == null) {
                return false;
            }
            // No need for further checking here, since 404 will throw an exception
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
        return true;
    }
}
