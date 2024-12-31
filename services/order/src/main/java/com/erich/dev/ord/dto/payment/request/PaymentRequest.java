package com.erich.dev.ord.dto.payment.request;

import com.erich.dev.ord.dto.customer.response.CustomerResponse;
import com.erich.dev.ord.entity.PaymentMethod;

import java.math.BigDecimal;

public record PaymentRequest(
        Long orderId,
        PaymentMethod paymentMethod,
        BigDecimal amount,
        String reference,
        CustomerResponse customer
) {
}
