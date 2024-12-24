package com.erich.dev.ord.service.impl;

import com.erich.dev.ord.dto.product.request.ProductPurchaseRequest;
import com.erich.dev.ord.dto.product.response.ProductPurchaseResponse;
import com.erich.dev.ord.dto.request.OrderLineRequest;
import com.erich.dev.ord.dto.request.OrderRequest;
import com.erich.dev.ord.dto.customer.response.CustomerResponse;
import com.erich.dev.ord.dto.response.OrderResponse;
import com.erich.dev.ord.entity.Order;
import com.erich.dev.ord.exception.NotFoundException;
import com.erich.dev.ord.kafka.OrderConfirmation;
import com.erich.dev.ord.kafka.producer.OrderProducer;
import com.erich.dev.ord.proxy.CustomerClient;
import com.erich.dev.ord.proxy.ProductClient;
import com.erich.dev.ord.repository.OrderRepository;
import com.erich.dev.ord.service.OrderLineService;
import com.erich.dev.ord.service.OrderService;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service @Slf4j @RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {


    private final OrderRepository orderRepository;

    private final CustomerClient customerClient;

    private final ProductClient productClient;

    private final OrderLineService orderLineService;

    private final OrderProducer orderProducer;


    @Override
    @Transactional
    public long createOrder(OrderRequest orderRequest) {
        //customer
        var customer = this.findCustomerId(orderRequest.customerId());

        //product
        var productPurchaseResponses = this.productPurchase(orderRequest.products());

        //persist order
        Order order = orderRepository.save(toOrder(orderRequest));

        //persits order lines
        for (ProductPurchaseRequest purchaseRequest : orderRequest.products()) {
            this.orderLineService.saveOrderLine(new OrderLineRequest(
                    null,
                    order.getId(),
                    purchaseRequest.productId(),
                    purchaseRequest.quantity()
            ));
        }
        OrderConfirmation orderConfirmation = new OrderConfirmation(
                orderRequest.reference(),
                orderRequest.totalAmount(),
                orderRequest.paymentMethod(),
                customer,
                productPurchaseResponses
        );
        orderProducer.sendOrderConfirmation(orderConfirmation);
        return order.getId();
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderResponse> findAllOrders() {
        return orderRepository.findAll().stream().map(this::fromOrder).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public OrderResponse findOrderById(Long id) {
        return orderRepository.findById(id)
                .map(this::fromOrder)
                .orElseThrow(() -> new NotFoundException("Order not found"));
    }

    private OrderResponse fromOrder(Order order){
        return new OrderResponse(
                order.getId(),
                order.getReference(),
                order.getTotalAmount(),
                order.getPaymentMethod(),
                order.getCustomerId()
                );
    }

    private Order toOrder(OrderRequest orderRequest){

        return Order.builder()
                .id(orderRequest.id())
                .reference(orderRequest.reference())
                .totalAmount(orderRequest.totalAmount())
                .paymentMethod(orderRequest.paymentMethod())
                .customerId(orderRequest.customerId())
                .build();
    }



    public List<ProductPurchaseResponse> productPurchase(List<ProductPurchaseRequest> productRequests) {
        try {
            log.info("Purchasing products: {}", productRequests);
            return this.productClient.pucharseProduct(productRequests);
        } catch (FeignException e) {
            log.error("Product not found or not available");
            throw new RuntimeException("Product not found or not available");
        }
    }

    public CustomerResponse findCustomerId(String customerId) {
        try {
            log.info("Finding customer by id: {}", customerId);
            return this.customerClient.findById(customerId);
        } catch (FeignException e) {
            log.error("Customer not found or not available");
            e.printStackTrace();
            throw new RuntimeException("Customer not found or not available");
        }
    }
}
