package com.erich.dev.ord.service;

import com.erich.dev.ord.dto.request.OrderRequest;

public interface OrderService {

    long createOrder(OrderRequest orderRequest);
}
