package com.github.andersonribeir0.banktransfers.commands;

import com.github.andersonribeir0.banktransfers.events.EventType;

import java.math.BigDecimal;

public class FinancialOperationCommand implements Command {

    private final String targetAccount;
    private final BigDecimal amount;
    private final EventType eventType;

    public FinancialOperationCommand(String targetAccount, BigDecimal amount, EventType eventType) {
        this.targetAccount = targetAccount;
        this.amount = amount;
        this.eventType = eventType;
    }

    public FinancialOperationCommand() {
        this(null, null, null);
    }

    public String getTargetAccount() {
        return targetAccount;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public EventType getEventType() {
        return eventType;
    }
}
