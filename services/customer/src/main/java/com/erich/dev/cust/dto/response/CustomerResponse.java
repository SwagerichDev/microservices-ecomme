package com.erich.dev.cust.dto.response;

import com.erich.dev.cust.entity.Address;

public record CustomerResponse(
        String firstName,
        String lastName,
        String email,
        Address address
) {
}
