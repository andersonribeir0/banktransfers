package com.github.andersonribeir0.banktransfers.commands;

public class TransferCommand {
    private final String from;
    private final String to;
    private final double amount;

    public TransferCommand(String from, String to, double amount) {
        this.from = from;
        this.to = to;
        this.amount = amount;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public double getAmount() {
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
