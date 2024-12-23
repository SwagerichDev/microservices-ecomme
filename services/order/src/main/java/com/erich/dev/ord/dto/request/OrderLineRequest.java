package com.erich.dev.ord.dto.request;

public record OrderLineRequest(
        Long id,
        Long orderId,
        Long productId,
        double quantity
) {
}
