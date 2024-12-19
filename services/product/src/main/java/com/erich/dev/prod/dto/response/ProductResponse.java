package com.erich.dev.prod.dto.response;

import java.math.BigDecimal;

public record ProductResponse(
    String name,
    String description,
    double availableQuantity,
    BigDecimal price,
    Long categoryId,
    String categoryName,
    String categoryDescription
) {
}
