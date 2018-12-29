package com.github.andersonribeir0.banktransfers;

import com.github.andersonribeir0.banktransfers.events.TransferCreated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class EventDispatcher {

    private final KafkaTemplate<String, Object> template;
    private final String topic;

    @Autowired
    public EventDispatcher(final KafkaTemplate<String, Object> template, @Value("${banktransfers.topic}") final String topic) {
        this.template = template;
        this.topic = topic;
    }

    public void dispatch(TransferCreated transferCreated) {
        this.template.send(topic, transferCreated.getId(), transferCreated);
    }
}
