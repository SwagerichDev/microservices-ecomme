package com.erick.dev.noti.kafka;


import com.erick.dev.noti.email.EmailService;
import com.erick.dev.noti.email.NotificationEmailService;
import com.erick.dev.noti.entity.Notification;
import com.erick.dev.noti.entity.NotificationType;
import com.erick.dev.noti.kafka.order.OrderConfirmation;
import com.erick.dev.noti.kafka.payment.PaymentConfirmation;
import com.erick.dev.noti.repository.NotificationRepository;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service @RequiredArgsConstructor @Slf4j
public class NotificationConsumer {

    private final NotificationRepository notificationRepository;

    private final EmailService emailService;

   // private final NotificationEmailService notificationEmailService;

    @KafkaListener(topics = "payment-topic")
    public void consumePaymentSuccessNotification(PaymentConfirmation paymentConfirmation) throws MessagingException {
        log.info("Consuming payment success notification: {}", paymentConfirmation);

        Notification notification = Notification.builder()
                .notificationType(NotificationType.PAYMENT_CONFIRMATION)
                .notificationDate(LocalDateTime.now())
                .paymentConfirmation(paymentConfirmation)
                .build();
        notificationRepository.save(notification);

        var customerName = paymentConfirmation.customerFirstName() + " " + paymentConfirmation.customerLastName();
        emailService.sendPaymentSuccessEmail(
                paymentConfirmation.customerEmail(),
                customerName,
                paymentConfirmation.amount(),
                paymentConfirmation.orderReference()
        );
      //  notificationEmailService.sendPaymentConfirmation(paymentConfirmation);
    }

    @KafkaListener(topics = "order-topic")
    public void consumeOrderConfirmationNotification(OrderConfirmation orderConfirmation) throws MessagingException {
        log.info("Consuming order success notification: {}", orderConfirmation);

        Notification notification = Notification.builder()
                .notificationType(NotificationType.ORDER_COFIRMATION)
                .notificationDate(LocalDateTime.now())
                .orderConfirmation(orderConfirmation)
                .build();
        notificationRepository.save(notification);

        var customerName = orderConfirmation.customer().firstName() + " " + orderConfirmation.customer().lastName();
        emailService.sendOrderConfirmationEmail(
                orderConfirmation.customer().email(),
                customerName,
                orderConfirmation.totalAmount(),
                orderConfirmation.orderReference(),
                orderConfirmation.products()
        );
        //notificationEmailService.sendOrderConfirmation(orderConfirmation);

    }
}
