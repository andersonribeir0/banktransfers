package com.github.andersonribeir0.banktransfers.events.dispatchers;

import com.github.andersonribeir0.banktransfers.events.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class DebitEventDispatcherImpl implements EventDispatcher {

    private final KafkaTemplate<String, Object> template;
    private final String topic;

    @Autowired
    public DebitEventDispatcherImpl(KafkaTemplate<String, Object> template, @Value("${debits.topic}") String topic) {
        this.template = template;
        this.topic = topic;
    }

    @Override
    public void dispatch(String key, Event event) {
        this.template.send(topic, key, event);
    }
}
