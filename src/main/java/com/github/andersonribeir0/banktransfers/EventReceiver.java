package com.github.andersonribeir0.banktransfers;

import com.github.andersonribeir0.banktransfers.commands.FinancialOperationCommand;
import com.github.andersonribeir0.banktransfers.events.*;
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
    private static final Logger logger = LoggerFactory.getLogger(EventReceiver.class.getName());

    private final KafkaTemplate<String, Object> template;
    @Qualifier("debitEventProcessorImpl")
    private final EventProcessor<FinancialOperationCommand> debitEventProcessorImpl;
    @Qualifier("creditEventProcessorImpl")
    private final EventProcessor<FinancialOperationCommand> creditEventProcessorImpl;

    @Autowired
    public EventReceiver(final KafkaTemplate<String, Object> template, EventProcessor<FinancialOperationCommand> debitEventProcessorImpl, EventProcessor<FinancialOperationCommand> creditEventProcessorImpl) {
        this.template = template;
        this.debitEventProcessorImpl = debitEventProcessorImpl;
        this.creditEventProcessorImpl = creditEventProcessorImpl;
    }

    @KafkaListener(topics = "transfers", clientIdPrefix = "json", containerFactory = "kafkaListenerContainerFactory")
    public void listenTransfers(ConsumerRecord<String, TransferCreated> cr, @Payload TransferCreated transferCreated) {
        logger.info("Logger transfers [JSON] received key {}: Type [{}] | Payload: {} | Record: {}", cr.key(),
                typeIdHeader(cr.headers()), transferCreated, cr.toString());
        FinancialOperationCommand debitOperation = new FinancialOperationCommand(transferCreated.getFrom(), transferCreated.getAmount(), EventType.DEBIT);
        FinancialOperationCommand creditOperation = new FinancialOperationCommand(transferCreated.getTo(), transferCreated.getAmount(), EventType.CREDIT);
        String debitEventId = debitEventProcessorImpl.process(debitOperation);
        String creditEventId = creditEventProcessorImpl.process(creditOperation);
        logger.info("Débito: {}\tCrédito: {}", debitEventId, creditEventId);
    }

    @KafkaListener(topics = "debits", clientIdPrefix = "json", containerFactory = "kafkaListenerContainerFactory")
    public void listenDebits(ConsumerRecord<String, DebitDispatched> cr, @Payload DebitDispatched debitDispatched) {
        logger.info("Logger debits [JSON] received key {}: Type [{}] | Payload: {} | Record: {}", cr.key(),
                typeIdHeader(cr.headers()), debitDispatched, cr.toString());

    }

    @KafkaListener(topics = "credits", clientIdPrefix = "json", containerFactory = "kafkaListenerContainerFactory")
    public void listenCredits(ConsumerRecord<String, DebitDispatched> cr, @Payload CreditDispatched creditDispatched) {
        logger.info("Logger credits [JSON] received key {}: Type [{}] | Payload: {} | Record: {}", cr.key(),
                typeIdHeader(cr.headers()), creditDispatched, cr.toString());

    }

    private static String typeIdHeader(Headers headers) {
        return StreamSupport.stream(headers.spliterator(), false)
                .filter(header -> header.key().equals("__TypeId__"))
                .findFirst().map(header -> new String(header.value())).orElse("N/A");
    }
}
