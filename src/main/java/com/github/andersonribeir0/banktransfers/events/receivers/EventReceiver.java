package com.github.andersonribeir0.banktransfers.events.receivers;

import com.github.andersonribeir0.banktransfers.events.Event;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.messaging.handler.annotation.Payload;

public interface EventReceiver<T extends Event> {
    void listen(ConsumerRecord<String, T> consumerRecord, @Payload T event);
}