package com.erich.dev.ord.service.impl;

import com.erich.dev.ord.dto.request.OrderLineRequest;
import com.erich.dev.ord.entity.Order;
import com.erich.dev.ord.entity.OrderLine;
import com.erich.dev.ord.repository.OrderLineRepository;
import com.erich.dev.ord.service.OrderLineService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service @RequiredArgsConstructor
public class OrderLineServiceImpl implements OrderLineService {

    private final OrderLineRepository orderLineRepository;


    @Override
    @Transactional
    public Long saveOrderLine(OrderLineRequest orderLineRequest) {
         return orderLineRepository.save(toOrderLine(orderLineRequest)).getId();
    }

    private OrderLine toOrderLine(OrderLineRequest orderLineRequest){
        return OrderLine.builder()
                .order(Order.builder().id(orderLineRequest.orderId()).build())
                .productId(orderLineRequest.productId())
                .quantity(orderLineRequest.quantity())
                .build();
    }
}
