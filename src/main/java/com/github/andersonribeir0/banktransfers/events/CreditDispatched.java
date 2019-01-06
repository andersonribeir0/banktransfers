package com.github.andersonribeir0.banktransfers.events;

import java.math.BigDecimal;

public class CreditDispatched extends Event{
    private final String targetAccount;
    private final BigDecimal amount;

    public CreditDispatched(String targetAccount, BigDecimal amount) {
        super(EventType.CREDIT);
        this.targetAccount = targetAccount;
        this.amount = amount;
    }

    public CreditDispatched() {
        this(null, null);
    }

    public String getTargetAccount() {
        return targetAccount;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return "CreditDispatched{" +
                "targetAccount='" + targetAccount + '\'' +
                ", amount=" + amount +
                '}';
    }
}
