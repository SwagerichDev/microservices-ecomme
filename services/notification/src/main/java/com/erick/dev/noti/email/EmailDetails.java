package com.erick.dev.noti.email;

import lombok.Getter;

import java.math.BigDecimal;
import java.util.Map;

@Getter
public abstract class EmailDetails {

    protected final String destinationEmail;
    protected final String customerName;
    protected final BigDecimal amount;
    protected final String orderReference;
    protected final EmailTemplate template;

    protected EmailDetails(String destinationEmail, String customerName,
                           BigDecimal amount, String orderReference, EmailTemplate template) {
        this.destinationEmail = destinationEmail;
        this.customerName = customerName;
        this.amount = amount;
        this.orderReference = orderReference;
        this.template = template;
    }

    public abstract Map<String, Object> createTemplateModel();
}
