package com.github.andersonribeir0.banktransfers.commands;

import java.math.BigDecimal;

public class TransferCommand implements Command {
    private final String from;
    private final String to;
    private final BigDecimal amount;

    public TransferCommand(String from, String to, BigDecimal amount) {
        this.from = from;
        this.to = to;
        this.amount = amount;
    }

    public TransferCommand() {
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

    @Override
    public String toString() {
        return "TransferCommand{" +
                "from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", amount=" + amount +
                '}';
    }
}
