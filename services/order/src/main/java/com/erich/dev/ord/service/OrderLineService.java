package com.erich.dev.ord.service;

import com.erich.dev.ord.dto.request.OrderLineRequest;

public interface OrderLineService {

    Long saveOrderLine(OrderLineRequest orderLineRequest);
}
