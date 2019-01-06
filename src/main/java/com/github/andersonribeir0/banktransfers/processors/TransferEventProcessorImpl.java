package com.github.andersonribeir0.banktransfers.processors;

import com.github.andersonribeir0.banktransfers.EventDispatcher;
import com.github.andersonribeir0.banktransfers.commands.TransferCommand;
import com.github.andersonribeir0.banktransfers.events.TransferCreated;
import com.github.andersonribeir0.banktransfers.exceptions.NoSuchEventException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.rmi.NoSuchObjectException;

@Service
public class TransferEventProcessorImpl implements EventProcessor<TransferCommand> {
    private static final Logger logger = LoggerFactory.getLogger(TransferEventProcessorImpl.class.getName());

    private final EventDispatcher eventDispatcher;

    @Autowired
    public TransferEventProcessorImpl(EventDispatcher eventDispatcher) {
        this.eventDispatcher = eventDispatcher;
    }

    @Override
    public String process(TransferCommand command) {
        try {
            TransferCreated transferCreated = new TransferCreated(command.getFrom(), command.getTo(), command.getAmount());
            this.eventDispatcher.dispatch(transferCreated.getId(), transferCreated);
            return transferCreated.getId();
        } catch (NoSuchEventException e) {
            logger.error("Erro ao processar evento de transferência de fundos. \n" + command.toString());
            return "";
        }
    }
}
