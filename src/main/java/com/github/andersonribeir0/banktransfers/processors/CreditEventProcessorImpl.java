package com.github.andersonribeir0.banktransfers.processors;

import com.github.andersonribeir0.banktransfers.commands.FinancialOperationCommand;
import com.github.andersonribeir0.banktransfers.events.CreditDispatched;
import com.github.andersonribeir0.banktransfers.events.dispatchers.EventDispatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;


@Service
public class CreditEventProcessorImpl implements EventProcessor<FinancialOperationCommand> {
    private static final Logger logger = LoggerFactory.getLogger(CreditEventProcessorImpl.class.getName());

    private final EventDispatcher eventDispatcher;

    @Autowired
    public CreditEventProcessorImpl(@Qualifier("creditEventDispatcherImpl") EventDispatcher eventDispatcher) {
        this.eventDispatcher = eventDispatcher;
    }

    @Override
    public String process(FinancialOperationCommand command) {
        CreditDispatched creditDispatched = new CreditDispatched(command.getTargetAccount(), command.getAmount());
        this.eventDispatcher.dispatch(creditDispatched.getTargetAccount(), creditDispatched);
        return creditDispatched.getId();
    }
}
