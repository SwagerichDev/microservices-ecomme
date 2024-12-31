package com.erich.dev.ord.proxy;

import com.erich.dev.ord.dto.payment.request.PaymentRequest;
import org.springframework.cloud.openfeign.FeignClient;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "payment-service", url = "${application.config.payment-url}")
public interface PaymentClient {

    @PostMapping
    Long createPayment(@RequestBody PaymentRequest paymentRequest);
}
