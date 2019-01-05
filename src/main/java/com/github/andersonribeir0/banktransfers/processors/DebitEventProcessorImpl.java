package com.github.andersonribeir0.banktransfers.processors;

import com.github.andersonribeir0.banktransfers.EventDispatcher;
import com.github.andersonribeir0.banktransfers.commands.DebitCommand;
import com.github.andersonribeir0.banktransfers.events.DebitDispatched;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.rmi.NoSuchObjectException;

@Service
public class DebitEventProcessorImpl implements EventProcessor<DebitCommand> {
    private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

    private final EventDispatcher eventDispatcher;
    public DebitEventProcessorImpl(EventDispatcher eventDispatcher) {
        this.eventDispatcher = eventDispatcher;
    }

    @Override
    public String process(DebitCommand debitCommand) {
        try {
            DebitDispatched debitDispatched = new DebitDispatched(debitCommand.getTargetAccount(), debitCommand.getAmount());
            this.eventDispatcher.dispatch(debitDispatched.getTargetAccount(), debitDispatched);
            return debitDispatched.getId();
        } catch (NoSuchObjectException e) {
            logger.error("Erro ao processar evento de débito para conta {0} no valor de {1}.\n" + e.getMessage(), debitCommand.getAmount(), debitCommand.getAmount().toString());
            return "";
        }
    }
}
