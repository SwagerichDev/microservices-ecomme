package com.erich.dev.ord.restcontroller;

import com.erich.dev.ord.dto.response.OrderLineResponse;
import com.erich.dev.ord.service.OrderLineService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/order-lines")
public class OrderLineRestController {


    private final OrderLineService orderLineService;

    public OrderLineRestController(OrderLineService orderLineService) {
        this.orderLineService = orderLineService;
    }

    @GetMapping("/order/{order-id}")
    public ResponseEntity<List<OrderLineResponse>> getOrderLines(@PathVariable("order-id") Long orderId) {
        return ResponseEntity.ok(orderLineService.findOrderLinesByOrderId(orderId));
    }
}
