package com.erich.dev.ord.dto.request;

import com.erich.dev.ord.dto.product.request.ProductPurchaseRequest;
import com.erich.dev.ord.entity.PaymentMethod;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.util.List;

public record OrderRequest(

        Long id,

        @NotNull
        String reference,

        @Positive
        BigDecimal totalAmount,

        @NotNull
        PaymentMethod paymentMethod,

        @NotNull
        @NotEmpty
        String customerId,

        @NotEmpty
        List<ProductPurchaseRequest> products

) {

}
