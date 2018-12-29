package com.github.andersonribeir0.banktransfers.controllers;

import com.github.andersonribeir0.banktransfers.EventDispatcher;
import com.github.andersonribeir0.banktransfers.commands.TransferCommand;
import com.github.andersonribeir0.banktransfers.events.TransferCreated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("transfer")
public class TransferController {

    private final EventDispatcher eventDispatcher;

    @Autowired
    public TransferController(EventDispatcher eventDispatcher) {
        this.eventDispatcher = eventDispatcher;
    }

    @PutMapping
    public ResponseEntity transfer(@RequestBody TransferCommand transferCommand) {
        this.eventDispatcher.dispatch(new TransferCreated(transferCommand.getFrom(), transferCommand.getTo(), transferCommand.getAmount()));
        return ResponseEntity.accepted().build();
    }
}
