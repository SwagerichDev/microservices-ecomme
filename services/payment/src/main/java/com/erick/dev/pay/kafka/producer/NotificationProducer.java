package com.erick.dev.pay.kafka.producer;

import com.erick.dev.pay.kafka.PaymentNotification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationProducer {

    private final KafkaTemplate<String, PaymentNotification> kafkaTemplate;

    public void sendNotification(PaymentNotification request) {
        log.info("Sending notification with body <{}>", request);

        Message<PaymentNotification> paymentNotificationMessage = MessageBuilder
                .withPayload(request).setHeader(KafkaHeaders.TOPIC, "payment-topic").build();

        kafkaTemplate.send(paymentNotificationMessage);

    }
}
