package com.erick.dev.pay.dto.request;

import com.erick.dev.pay.dto.customer.Customer;
import com.erick.dev.pay.entity.PaymentMethod;

import java.math.BigDecimal;

public record PaymentRequest(
        Long orderId,
        PaymentMethod paymentMethod,
        BigDecimal amount,
        String reference,
        Customer customer
) {
}
