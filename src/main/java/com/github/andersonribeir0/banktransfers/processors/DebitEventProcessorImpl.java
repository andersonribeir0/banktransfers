package com.github.andersonribeir0.banktransfers.processors;

import com.github.andersonribeir0.banktransfers.EventDispatcher;
import com.github.andersonribeir0.banktransfers.commands.FinancialOperationCommand;
import com.github.andersonribeir0.banktransfers.events.DebitDispatched;
import com.github.andersonribeir0.banktransfers.exceptions.NoSuchEventException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.rmi.NoSuchObjectException;

@Service
public class DebitEventProcessorImpl implements EventProcessor<FinancialOperationCommand> {
    private static final Logger logger = LoggerFactory.getLogger(DebitEventProcessorImpl.class.getName());

    private final EventDispatcher eventDispatcher;
    public DebitEventProcessorImpl(EventDispatcher eventDispatcher) {
        this.eventDispatcher = eventDispatcher;
    }

    @Override
    public String process(FinancialOperationCommand financialOperationCommand) {
        try {
            DebitDispatched debitDispatched = new DebitDispatched(financialOperationCommand.getTargetAccount(), financialOperationCommand.getAmount());
            this.eventDispatcher.dispatch(debitDispatched.getTargetAccount(), debitDispatched);
            return debitDispatched.getId();
        } catch (NoSuchEventException e) {
            logger.error("Erro ao processar evento de débito para conta {0} no valor de {1}.\n" + e.getMessage(), financialOperationCommand.getAmount(), financialOperationCommand.getAmount().toString());
            return "";
        }
    }
}
