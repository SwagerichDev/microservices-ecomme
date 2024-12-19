package com.erich.dev.prod.dto.response;

import java.math.BigDecimal;

public record ProductPurchaseResponse(

        Long id,
        String name,
        String description,
        double availableQuantity,
        BigDecimal price

) {
}
