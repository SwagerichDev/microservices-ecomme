package com.erick.dev.pay.kafka;

import com.erick.dev.pay.entity.PaymentMethod;

import java.math.BigDecimal;

public record PaymentNotification(

        String orderReference,
        BigDecimal amount,
        PaymentMethod paymentMethod,
        String customerFirstName,
        String customerEmail,
        String customerLastName
) {
}
