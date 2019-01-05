package com.github.andersonribeir0.banktransfers.events;

import java.math.BigDecimal;

public class TransferCreated extends Event{
    private final String from;
    private final String to;
    private final BigDecimal amount;
    private final EventType eventType = EventType.TRANSFER;

    public TransferCreated(String from, String to, BigDecimal amount) {
        super();
        this.from = from;
        this.to = to;
        this.amount = amount;
    }

    public TransferCreated() {
        this(null,null,null);
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public EventType getEventType() {
        return eventType;
    }

    @Override
    public String toString() {
        return "TransferCreated{" +
                "eventType='" + eventType + '\'' +
                "from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", amount=" + amount +
                '}';
    }
}
