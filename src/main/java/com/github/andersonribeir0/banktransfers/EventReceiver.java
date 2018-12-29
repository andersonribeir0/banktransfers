package com.github.andersonribeir0.banktransfers;

import com.github.andersonribeir0.banktransfers.events.TransferCreated;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.header.Headers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.stream.StreamSupport;

@Service
public class EventReceiver {
    private static final Logger logger = LoggerFactory.getLogger(EventReceiver.class);

    private final KafkaTemplate<String, Object> template;

    @Autowired
    public EventReceiver(final KafkaTemplate<String, Object> template) {
        this.template = template;
    }

    @KafkaListener(topics = "transfer-topic", clientIdPrefix = "json", containerFactory = "kafkaListenerContainerFactory")
    public void listen(ConsumerRecord<String, TransferCreated> cr, @Payload TransferCreated transferCreated) {
        logger.info("Logger 1 [JSON] received key {}: Type [{}] | Payload: {} | Record: {}", cr.key(),
                typeIdHeader(cr.headers()), transferCreated, cr.toString());
    }

    private static String typeIdHeader(Headers headers) {
        return StreamSupport.stream(headers.spliterator(), false)
                .filter(header -> header.key().equals("__TypeId__"))
                .findFirst().map(header -> new String(header.value())).orElse("N/A");
    }
}
