package com.erick.dev.noti.email;

import com.erick.dev.noti.kafka.order.OrderConfirmation;
import com.erick.dev.noti.kafka.payment.PaymentConfirmation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationEmailService {

    private final EmailService emailService;

    public void sendPaymentConfirmation(PaymentConfirmation paymentConfirmation) {
        PaymentConfirmationEmail emailDetails = PaymentConfirmationEmail.builder()
                .destinationEmail(paymentConfirmation.customerEmail())
                .customerName(paymentConfirmation.customerFirstName() + " " + paymentConfirmation.customerLastName())
                .amount(paymentConfirmation.amount())
                .orderReference(paymentConfirmation.orderReference())
                .build();

        emailService.sendTemplatedEmail(emailDetails);
    }

    public void sendOrderConfirmation(OrderConfirmation orderConfirmation) {
        OrderConfirmationEmail emailDetails = OrderConfirmationEmail.builder()
                .destinationEmail(orderConfirmation.customer().email())
                .customerName(orderConfirmation.customer().firstName() + " " + orderConfirmation.customer().lastName())
                .amount(orderConfirmation.totalAmount())
                .orderReference(orderConfirmation.orderReference())
                .products(orderConfirmation.products())
                .build();

        emailService.sendTemplatedEmail(emailDetails);
    }
}
