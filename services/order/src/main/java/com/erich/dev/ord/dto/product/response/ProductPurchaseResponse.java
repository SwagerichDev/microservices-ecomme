package com.erich.dev.ord.dto.product.response;

import java.math.BigDecimal;

public record ProductPurchaseResponse(
        Long id,
        String name,
        String description,
        double availableQuantity,
        BigDecimal price
) {
}
