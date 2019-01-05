package com.github.andersonribeir0.banktransfers.commands;

import java.math.BigDecimal;

public class DebitCommand implements Command {
    private final String targetAccount;
    private final BigDecimal amount;

    public DebitCommand(String targetAccount, BigDecimal amount) {
        this.targetAccount = targetAccount;
        this.amount = amount;
    }

    public DebitCommand() {
        this(null,null);
    }

    public String getTargetAccount() {
        return targetAccount;
    }

    public BigDecimal getAmount() {
        return amount;
    }
}
