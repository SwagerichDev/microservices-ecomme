package com.erich.dev.ord.dto.response;

import com.erich.dev.ord.entity.PaymentMethod;

import java.math.BigDecimal;

public record OrderResponse(

        Long id,

        String reference,

        BigDecimal amount,

        PaymentMethod paymentMethod,

        String customerId

) {
}
