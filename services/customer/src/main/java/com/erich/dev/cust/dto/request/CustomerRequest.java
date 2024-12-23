package com.erich.dev.cust.dto.request;

import com.erich.dev.cust.entity.Address;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;


public record CustomerRequest(
        @NotNull(message = "First name is required")
        String firstName,
        @NotNull(message = "Last name is required")
        String lastName,
        @NotNull(message = "Email is required")
        @Email(message = "Email should be valid")
        String email,
        Address address) {
}
