package com.github.andersonribeir0.banktransfers.processors;

import com.github.andersonribeir0.banktransfers.EventDispatcher;
import com.github.andersonribeir0.banktransfers.commands.FinancialOperationCommand;
import com.github.andersonribeir0.banktransfers.events.CreditDispatched;
import com.github.andersonribeir0.banktransfers.exceptions.NoSuchEventException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.rmi.NoSuchObjectException;

@Service
public class CreditEventProcessorImpl implements EventProcessor<FinancialOperationCommand> {
    private static final Logger logger = LoggerFactory.getLogger(CreditEventProcessorImpl.class.getName());

    private final EventDispatcher eventDispatcher;

    public CreditEventProcessorImpl(EventDispatcher eventDispatcher) {
        this.eventDispatcher = eventDispatcher;
    }

    @Override
    public String process(FinancialOperationCommand command) {
        try {
            CreditDispatched creditDispatched = new CreditDispatched(command.getTargetAccount(), command.getAmount());
            this.eventDispatcher.dispatch(creditDispatched.getTargetAccount(), creditDispatched);
            return creditDispatched.getId();
        } catch (NoSuchEventException e) {
            logger.error("Erro ao processar evento de crédito para conta {0} no valor de {1}.\n" + e.getMessage(), command.getAmount(), command.getAmount().toString());
            return "";
        }
    }
}
