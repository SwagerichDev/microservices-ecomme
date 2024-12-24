package com.erick.dev.pay.service;

import com.erick.dev.pay.dto.request.PaymentRequest;

public interface PaymentService {

    Long createPayment(PaymentRequest paymentRequest);
}
