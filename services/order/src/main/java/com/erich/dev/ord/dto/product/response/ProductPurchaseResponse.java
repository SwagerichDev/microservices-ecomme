package com.erich.dev.ord.dto.product.response;

import java.math.BigDecimal;

public record ProductPurchaseResponse(
        Long productId,
        String name,
        String description,
        BigDecimal price,
        double quantity
) {
}
