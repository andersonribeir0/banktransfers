package com.github.andersonribeir0.banktransfers.events.receivers;

import com.github.andersonribeir0.banktransfers.commands.FinancialOperationCommand;
import com.github.andersonribeir0.banktransfers.events.EventType;
import com.github.andersonribeir0.banktransfers.events.TransferCreated;
import com.github.andersonribeir0.banktransfers.processors.EventProcessor;
import com.github.andersonribeir0.banktransfers.utils.KafkaUtils;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class TransferEventReceiver implements EventReceiver<TransferCreated> {
    private static final Logger logger = LoggerFactory.getLogger(TransferEventReceiver.class.getName());

    private final EventProcessor<FinancialOperationCommand> debitEventProcessorImpl;
    private final EventProcessor<FinancialOperationCommand> creditEventProcessorImpl;

    @Autowired
    public TransferEventReceiver(@Qualifier("debitEventProcessorImpl") EventProcessor<FinancialOperationCommand> debitEventProcessorImpl, @Qualifier("creditEventProcessorImpl") EventProcessor<FinancialOperationCommand> creditEventProcessorImpl) {
        this.debitEventProcessorImpl = debitEventProcessorImpl;
        this.creditEventProcessorImpl = creditEventProcessorImpl;
    }

    @KafkaListener(topics = "transfers", clientIdPrefix = "json", containerFactory = "kafkaListenerContainerFactory")
    public void listen(ConsumerRecord<String, TransferCreated> cr, @Payload TransferCreated transferCreated) {
        logger.info("Logger transfers [JSON] received key {}: Type [{}] | Payload: {} | Record: {}", cr.key(),
                KafkaUtils.typeIdHeader(cr.headers()), transferCreated, cr.toString());
        FinancialOperationCommand debitOperation = new FinancialOperationCommand(transferCreated.getFrom(), transferCreated.getAmount(), EventType.DEBIT);
        FinancialOperationCommand creditOperation = new FinancialOperationCommand(transferCreated.getTo(), transferCreated.getAmount(), EventType.CREDIT);
        String debitEventId = debitEventProcessorImpl.process(debitOperation);
        String creditEventId = creditEventProcessorImpl.process(creditOperation);
        logger.info("Debit: {}\tCredit: {}", debitEventId, creditEventId);
    }
}
