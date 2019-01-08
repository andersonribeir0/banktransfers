package com.github.andersonribeir0.banktransfers.events;

import java.math.BigDecimal;

public class TransferCreated extends Event {
    private final String from;
    private final String to;
    private final BigDecimal amount;

    public TransferCreated(String from, String to, BigDecimal amount) {
        super(EventType.TRANSFER);
        this.from = from;
        this.to = to;
        this.amount = amount;
    }

    public TransferCreated() {
        this(null, null, null);
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

    @Override
    public String toString() {
        return "TransferCreated{" +
                "from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", amount=" + amount +
                '}';
    }
}
