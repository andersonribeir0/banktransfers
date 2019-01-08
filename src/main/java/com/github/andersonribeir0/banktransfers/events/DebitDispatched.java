package com.github.andersonribeir0.banktransfers.events;

import java.math.BigDecimal;

public class DebitDispatched extends Event {
    private final String targetAccount;
    private final BigDecimal amount;

    public DebitDispatched(String targetAccount, BigDecimal amount) {
        super(EventType.DEBIT);
        this.targetAccount = targetAccount;
        this.amount = amount;
    }

    public DebitDispatched() {
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
        return "DebitDispatched{" +
                "targetAccount='" + targetAccount + '\'' +
                ", amount=" + amount +
                '}';
    }
}
