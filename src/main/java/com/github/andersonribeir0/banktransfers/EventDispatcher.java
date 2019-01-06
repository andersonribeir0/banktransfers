package com.github.andersonribeir0.banktransfers;

import com.github.andersonribeir0.banktransfers.events.CreditDispatched;
import com.github.andersonribeir0.banktransfers.events.DebitDispatched;
import com.github.andersonribeir0.banktransfers.events.Event;
import com.github.andersonribeir0.banktransfers.events.TransferCreated;
import com.github.andersonribeir0.banktransfers.exceptions.NoSuchEventException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;


@Service
public class EventDispatcher {

    private final KafkaTemplate<String, Object> template;
    private final String transfersTopic;
    private final String debitsTopic;
    private final String creditsTopic;

    @Autowired
    public EventDispatcher(final KafkaTemplate<String, Object> template, @Value("${transfers.topic}") final String transfersTopic, @Value("${debits.topic}") final String debitsTopic, @Value("credits") final String creditsTopic) {
        this.template = template;
        this.transfersTopic = transfersTopic;
        this.debitsTopic = debitsTopic;
        this.creditsTopic = creditsTopic;
    }

    public void dispatch(String key, Event event) {

        if(event instanceof DebitDispatched) {
            this.template.send(debitsTopic, key, event);
        } else if(event instanceof TransferCreated) {
            this.template.send(transfersTopic, key, event);
        } else if(event instanceof CreditDispatched) {
            this.template.send(creditsTopic, key, event);
        } else {
            throw new NoSuchEventException("Invalid event: " + event.toString());
        }

    }

}
