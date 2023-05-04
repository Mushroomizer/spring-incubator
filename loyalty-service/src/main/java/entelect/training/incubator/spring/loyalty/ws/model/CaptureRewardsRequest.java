package entelect.training.incubator.spring.loyalty.ws.model;

import java.math.BigDecimal;

public class CaptureRewardsRequest {
    private String passportNumber;
    private BigDecimal amount;

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getPassportNumber() {
        return passportNumber;
    }

    public void setPassportNumber(String passportNumber) {
        this.passportNumber = passportNumber;
    }
}
