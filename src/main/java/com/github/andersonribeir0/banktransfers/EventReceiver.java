package com.github.andersonribeir0.banktransfers;

import com.github.andersonribeir0.banktransfers.commands.DebitCommand;
import com.github.andersonribeir0.banktransfers.events.DebitDispatched;
import com.github.andersonribeir0.banktransfers.events.TransferCreated;
import com.github.andersonribeir0.banktransfers.processors.EventProcessor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.header.Headers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.stream.StreamSupport;

@Service
public class EventReceiver {
    private static final Logger logger = LoggerFactory.getLogger(EventReceiver.class);

    private final KafkaTemplate<String, Object> template;
    @Qualifier("debitEventProcessorImpl")
    private final EventProcessor debitEventProcessorImpl;

    @Autowired
    public EventReceiver(final KafkaTemplate<String, Object> template, EventProcessor debitEventProcessorImpl) {
        this.template = template;
        this.debitEventProcessorImpl = debitEventProcessorImpl;
    }

    @KafkaListener(topics = "transfers", clientIdPrefix = "json", containerFactory = "kafkaListenerContainerFactory")
    public void listenTransfers(ConsumerRecord<String, TransferCreated> cr, @Payload TransferCreated transferCreated) {
        logger.info("Logger transfers [JSON] received key {}: Type [{}] | Payload: {} | Record: {}", cr.key(),
                typeIdHeader(cr.headers()), transferCreated, cr.toString());
        DebitCommand debitCommand = new DebitCommand(transferCreated.getFrom(), transferCreated.getAmount());
        debitEventProcessorImpl.process(debitCommand);
    }

    @KafkaListener(topics = "debits", clientIdPrefix = "json", containerFactory = "kafkaListenerContainerFactory")
    public void listenDebits(ConsumerRecord<String, DebitDispatched> cr, @Payload DebitDispatched debitDispatched) {
        logger.info("Logger debits [JSON] received key {}: Type [{}] | Payload: {} | Record: {}", cr.key(),
                typeIdHeader(cr.headers()), debitDispatched, cr.toString());

    }

    private static String typeIdHeader(Headers headers) {
        return StreamSupport.stream(headers.spliterator(), false)
                .filter(header -> header.key().equals("__TypeId__"))
                .findFirst().map(header -> new String(header.value())).orElse("N/A");
    }
}
