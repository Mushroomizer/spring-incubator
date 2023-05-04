package entelect.training.incubator.spring.booking.rewards.client;

import entelect.training.incubator.spring.booking.rewards.client.gen.CaptureRewardsRequest;
import entelect.training.incubator.spring.booking.rewards.client.gen.CaptureRewardsResponse;
import entelect.training.incubator.spring.booking.rewards.client.gen.RewardsBalanceRequest;
import entelect.training.incubator.spring.booking.rewards.client.gen.RewardsBalanceResponse;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Component;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

import java.math.BigDecimal;

@Component
public class RewardsClient extends WebServiceGatewaySupport {

    public RewardsClient() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath("entelect.training.incubator.spring.booking.rewards.client.gen");

        this.setDefaultUri("http://localhost:8208/ws");
        this.setMarshaller(marshaller);
        this.setUnmarshaller(marshaller);
    }

    public CaptureRewardsResponse updateRewards(String passportNumber, BigDecimal amount) {
        CaptureRewardsRequest request = new CaptureRewardsRequest();
        request.setPassportNumber(passportNumber);
        request.setAmount(amount);

        return (CaptureRewardsResponse) getWebServiceTemplate().marshalSendAndReceive(request);
    }

    public RewardsBalanceResponse getRewardsBalance(String passportNumber) {
        RewardsBalanceRequest request = new RewardsBalanceRequest();
        request.setPassportNumber(passportNumber);

        return (RewardsBalanceResponse) getWebServiceTemplate().marshalSendAndReceive(request);
    }
}
