package com.erick.dev.pay.service.impl;

import com.erick.dev.pay.dto.request.PaymentRequest;
import com.erick.dev.pay.entity.Payment;
import com.erick.dev.pay.kafka.PaymentNotification;
import com.erick.dev.pay.kafka.producer.NotificationProducer;
import com.erick.dev.pay.repository.PaymentRepository;
import com.erick.dev.pay.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service @RequiredArgsConstructor @Slf4j
public class PaymentServiceImpl implements PaymentService {


    private final PaymentRepository paymentRepository;

    private final NotificationProducer notificationProducer;

    @Override
    public Long createPayment(PaymentRequest paymentRequest) {

        log.info("Creating payment for order {}", paymentRequest.orderId());

        Payment payment = paymentRepository.save(toPayment(paymentRequest));

        var  paymentNotification = new PaymentNotification(
          paymentRequest.reference(),
          paymentRequest.amount(),
          paymentRequest.paymentMethod(),
          paymentRequest.customer().firstName(),
          paymentRequest.customer().email(),
          paymentRequest.customer().lastName()
        );
        notificationProducer.sendNotification(paymentNotification);
        return payment.getId();
    }


    private Payment toPayment(PaymentRequest paymentRequest) {
        return Payment.builder()
                .orderId(paymentRequest.orderId())
                .paymentMethod(paymentRequest.paymentMethod())
                .amount(paymentRequest.amount())
                .build();
    }
}
