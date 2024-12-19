package com.erich.dev.prod.dto.request;

import jakarta.validation.constraints.NotNull;

public record ProductPurchaseRequest(

        @NotNull
        Long id,

        @NotNull
        double quantity
) {
}
