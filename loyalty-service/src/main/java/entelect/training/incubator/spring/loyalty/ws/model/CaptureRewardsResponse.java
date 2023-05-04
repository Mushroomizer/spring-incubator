package entelect.training.incubator.spring.loyalty.ws.model;

import java.math.BigDecimal;

public class CaptureRewardsResponse {
    private BigDecimal balance;

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}
