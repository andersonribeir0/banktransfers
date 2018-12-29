package com.github.andersonribeir0.banktransfers.events;

public class TransferCreated extends Event{
    private final String from;
    private final String to;
    private final double amount;

    public TransferCreated(String from, String to, double amount) {
        this.from = from;
        this.to = to;
        this.amount = amount;
    }

    public TransferCreated() {
        this(null,null,0);
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
        return "TransferCreated{" +
                "id='" + this.getId() + '\'' +
                "from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", amount=" + amount +
                '}';
    }
}
