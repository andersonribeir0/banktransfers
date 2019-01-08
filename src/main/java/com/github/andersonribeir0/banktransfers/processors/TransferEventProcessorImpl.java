package com.github.andersonribeir0.banktransfers.processors;

import com.github.andersonribeir0.banktransfers.commands.TransferCommand;
import com.github.andersonribeir0.banktransfers.events.TransferCreated;
import com.github.andersonribeir0.banktransfers.events.dispatchers.EventDispatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class TransferEventProcessorImpl implements EventProcessor<TransferCommand> {
    private static final Logger logger = LoggerFactory.getLogger(TransferEventProcessorImpl.class.getName());

    private final EventDispatcher eventDispatcher;

    @Autowired
    public TransferEventProcessorImpl(@Qualifier("transferEventDispatcherImpl") EventDispatcher eventDispatcher) {
        this.eventDispatcher = eventDispatcher;
    }

    @Override
    public String process(TransferCommand command) {
        TransferCreated transferCreated = new TransferCreated(command.getFrom(), command.getTo(), command.getAmount());
        this.eventDispatcher.dispatch(transferCreated.getId(), transferCreated);
        return transferCreated.getId();
    }
}
