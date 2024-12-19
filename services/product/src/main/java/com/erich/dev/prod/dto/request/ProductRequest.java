package com.erich.dev.prod.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record ProductRequest(

        @NotNull
        Long id,

        @NotEmpty
        String name,

        @NotEmpty
        String description,

        @Size(min = 1)
        @NotNull
        double availableQuantity,

        @NotNull
        @Positive
        BigDecimal price,

        @NotNull
        Long categoryId) {
}
