package com.erich.dev.cust.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.validation.annotation.Validated;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Validated
public class Address {

    @Id
    private String id;

    private String street;

    private String houseNumber;

    private String zipCode;


}
