package com.erick.dev.noti.email;

import com.erick.dev.noti.kafka.order.Product;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Getter
public class OrderConfirmationEmail extends EmailDetails{

    private final  List<Product> products;

    @Builder
    public OrderConfirmationEmail(String destinationEmail, String customerName,
                                  BigDecimal amount, String orderReference,
                                  List<Product> products) {
        super(destinationEmail, customerName, amount, orderReference,
                EmailTemplate.ORDER_CONFIRMATION);
        this.products = products;
    }

    @Override
    public Map<String, Object> createTemplateModel() {
        return Map.of(
                "customerName", customerName,
                "totalAmount", amount,
                "orderReference", orderReference,
                "products", products
        );
    }
}
