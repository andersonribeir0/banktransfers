package com.github.andersonribeir0.banktransfers.processors;

import com.github.andersonribeir0.banktransfers.commands.FinancialOperationCommand;
import com.github.andersonribeir0.banktransfers.events.DebitDispatched;
import com.github.andersonribeir0.banktransfers.events.dispatchers.EventDispatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class DebitEventProcessorImpl implements EventProcessor<FinancialOperationCommand> {
    private static final Logger logger = LoggerFactory.getLogger(DebitEventProcessorImpl.class.getName());

    private final EventDispatcher eventDispatcher;

    @Autowired
    public DebitEventProcessorImpl(@Qualifier("debitEventDispatcherImpl") EventDispatcher eventDispatcher) {
        this.eventDispatcher = eventDispatcher;
    }


    @Override
    public String process(FinancialOperationCommand financialOperationCommand) {
        DebitDispatched debitDispatched = new DebitDispatched(financialOperationCommand.getTargetAccount(), financialOperationCommand.getAmount());
        this.eventDispatcher.dispatch(debitDispatched.getTargetAccount(), debitDispatched);
        return debitDispatched.getId();
    }
}
