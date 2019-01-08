package com.github.andersonribeir0.banktransfers.events.receivers;

import com.github.andersonribeir0.banktransfers.events.DebitDispatched;
import com.github.andersonribeir0.banktransfers.utils.KafkaUtils;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class DebitEventReceiver implements EventReceiver<DebitDispatched> {
    private static final Logger logger = LoggerFactory.getLogger(DebitEventReceiver.class.getName());

    @KafkaListener(topics = "debits", clientIdPrefix = "json", containerFactory = "kafkaListenerContainerFactory")
    public void listen(ConsumerRecord<String, DebitDispatched> cr, @Payload DebitDispatched debitDispatched) {
        logger.info("Logger debits [JSON] received key {}: Type [{}] | Payload: {} | Record: {}", cr.key(),
                KafkaUtils.typeIdHeader(cr.headers()), debitDispatched, cr.toString());
    }

}
