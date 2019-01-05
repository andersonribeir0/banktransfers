package com.github.andersonribeir0.banktransfers.events;

import java.math.BigDecimal;

public class DebitDispatched extends Event{
    private final EventType eventType = EventType.DEBIT;
    private final String targetAccount;
    private final BigDecimal amount;

    public DebitDispatched(String targetAccount, BigDecimal amount) {
        super();
        this.targetAccount = targetAccount;
        this.amount = amount;
    }

    public DebitDispatched() {
        this(null, null);
    }

    public EventType getEventType() {
        return eventType;
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
                "eventType=" + eventType +
                ", targetAccount='" + targetAccount + '\'' +
                ", amount=" + amount +
                '}';
    }
}
