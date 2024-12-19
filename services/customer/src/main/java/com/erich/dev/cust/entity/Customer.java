package com.erich.dev.cust.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "customer")
public class Customer {

    @Id
    private String id;

    private String name;

    private String lastName;

    private String email;

    private Address address;
}
