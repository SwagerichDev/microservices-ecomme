package com.erich.dev.ord.service;

import com.erich.dev.ord.dto.request.OrderRequest;
import com.erich.dev.ord.dto.response.OrderResponse;

import java.util.List;

public interface OrderService {

    long createOrder(OrderRequest orderRequest);

    List<OrderResponse> findAllOrders();

    OrderResponse findOrderById(Long id);
}
