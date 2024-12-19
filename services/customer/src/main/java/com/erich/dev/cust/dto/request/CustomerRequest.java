package com.erich.dev.cust.dto.request;

import com.erich.dev.cust.entity.Address;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;


public record CustomerRequest(
        @NotEmpty(message = "First name is required")
        String firstName,
        @NotEmpty(message = "Last name is required")
        String lastName,
        @NotEmpty(message = "Email is required")
        @Email(message = "Email should be valid")
        String email,
        Address address) {
}
