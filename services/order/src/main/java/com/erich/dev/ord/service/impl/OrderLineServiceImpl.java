package com.erich.dev.ord.service.impl;

import com.erich.dev.ord.dto.request.OrderLineRequest;
import com.erich.dev.ord.dto.response.OrderLineResponse;
import com.erich.dev.ord.entity.Order;
import com.erich.dev.ord.entity.OrderLine;
import com.erich.dev.ord.repository.OrderLineRepository;
import com.erich.dev.ord.service.OrderLineService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service @RequiredArgsConstructor
public class OrderLineServiceImpl implements OrderLineService {

    private final OrderLineRepository orderLineRepository;


    @Override
    @Transactional
    public Long saveOrderLine(OrderLineRequest orderLineRequest) {
         return orderLineRepository.save(toOrderLine(orderLineRequest)).getId();
    }


    @Override
    @Transactional(readOnly = true)
    public List<OrderLineResponse> findOrderLinesByOrderId(Long orderId) {
        return orderLineRepository.findAllByOrderId(orderId).stream().map(this::fromOrderLine).toList();
    }

    private OrderLineResponse fromOrderLine(OrderLine orderLine){
        return new OrderLineResponse(
                orderLine.getId(),
                orderLine.getQuantity()
        );
    }

    private OrderLine toOrderLine(OrderLineRequest orderLineRequest){
        return OrderLine.builder()
                .order(Order.builder().id(orderLineRequest.orderId()).build())
                .productId(orderLineRequest.productId())
                .quantity(orderLineRequest.quantity())
                .build();
    }
}
