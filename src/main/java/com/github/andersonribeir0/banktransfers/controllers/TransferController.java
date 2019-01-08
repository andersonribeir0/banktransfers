package com.github.andersonribeir0.banktransfers.controllers;

import com.github.andersonribeir0.banktransfers.commands.TransferCommand;
import com.github.andersonribeir0.banktransfers.processors.EventProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("transfer")
public class TransferController {

    private final EventProcessor eventProcessor;

    @Autowired
    public TransferController(@Qualifier("transferEventProcessorImpl") EventProcessor eventProcessor) {
        this.eventProcessor = eventProcessor;
    }

    @PutMapping
    public ResponseEntity transfer(@RequestBody TransferCommand transferCommand) {
        String eventId = this.eventProcessor.process(transferCommand);
        if (!StringUtils.isEmpty(eventId)) {
            return ResponseEntity.accepted().body(eventId);
        }
        return ResponseEntity.badRequest().build();
    }
}
