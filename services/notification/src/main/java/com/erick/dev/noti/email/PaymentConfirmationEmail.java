package com.erick.dev.noti.email;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Getter
public class PaymentConfirmationEmail extends EmailDetails {

    @Builder
    public PaymentConfirmationEmail(String destinationEmail, String customerName,
                                    BigDecimal amount, String orderReference) {
        super(destinationEmail, customerName, amount, orderReference,
                EmailTemplate.PAYMENT_CONFIRMATION);
    }

    @Override
    public Map<String, Object> createTemplateModel() {
        Map<String, Object> model = new HashMap<>();
        model.put("customerName", customerName);
        model.put("amount", amount);
        model.put("orderReference", orderReference);
        return model;
    }
}
