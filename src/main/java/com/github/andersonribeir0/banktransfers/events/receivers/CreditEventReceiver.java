package com.github.andersonribeir0.banktransfers.events.receivers;

import com.github.andersonribeir0.banktransfers.events.CreditDispatched;
import com.github.andersonribeir0.banktransfers.utils.KafkaUtils;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class CreditEventReceiver implements EventReceiver<CreditDispatched> {
    private static final Logger logger = LoggerFactory.getLogger(CreditEventReceiver.class.getName());

    @KafkaListener(topics = "credits", clientIdPrefix = "json", containerFactory = "kafkaListenerContainerFactory")
    public void listen(ConsumerRecord<String, CreditDispatched> cr, @Payload CreditDispatched creditDispatched) {
        logger.info("Logger credits [JSON] received key {}: Type [{}] | Payload: {} | Record: {}", cr.key(),
                KafkaUtils.typeIdHeader(cr.headers()), creditDispatched, cr.toString());

    }

}
