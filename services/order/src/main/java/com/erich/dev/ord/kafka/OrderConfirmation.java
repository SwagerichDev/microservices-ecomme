package com.erich.dev.ord.kafka;

import com.erich.dev.ord.dto.product.response.ProductPurchaseResponse;
import com.erich.dev.ord.dto.customer.response.CustomerResponse;
import com.erich.dev.ord.entity.PaymentMethod;

import java.math.BigDecimal;
import java.util.List;

public record OrderConfirmation(

        String orderReference,

        BigDecimal totalAmount,

        PaymentMethod paymentMethod,

        CustomerResponse customer,

        List<ProductPurchaseResponse> products
) {
}
