package com.erich.dev.ord.service;

import com.erich.dev.ord.dto.request.OrderLineRequest;
import com.erich.dev.ord.dto.response.OrderLineResponse;

import java.util.List;

public interface OrderLineService {

    Long saveOrderLine(OrderLineRequest orderLineRequest);

    List<OrderLineResponse> findOrderLinesByOrderId(Long orderId);
}
