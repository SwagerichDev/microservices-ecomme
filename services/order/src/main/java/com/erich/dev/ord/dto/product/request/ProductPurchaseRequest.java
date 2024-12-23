package com.erich.dev.ord.dto.product.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ProductPurchaseRequest(
        @NotNull
        Long productId,

        @Positive
        double quantity

) {
}
