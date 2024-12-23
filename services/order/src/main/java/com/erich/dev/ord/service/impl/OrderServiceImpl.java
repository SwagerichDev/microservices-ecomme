package com.erich.dev.ord.service.impl;

import com.erich.dev.ord.dto.product.request.ProductPurchaseRequest;
import com.erich.dev.ord.dto.product.response.ProductPurchaseResponse;
import com.erich.dev.ord.dto.request.OrderLineRequest;
import com.erich.dev.ord.dto.request.OrderRequest;
import com.erich.dev.ord.dto.response.CustomerResponse;
import com.erich.dev.ord.entity.Order;
import com.erich.dev.ord.proxy.CustomerClient;
import com.erich.dev.ord.proxy.ProductClient;
import com.erich.dev.ord.repository.OrderRepository;
import com.erich.dev.ord.service.OrderLineService;
import com.erich.dev.ord.service.OrderService;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;


@Service @Slf4j @RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {


    private final OrderRepository orderRepository;

    private final CustomerClient customerClient;

    private final ProductClient productClient;

    private final OrderLineService orderLineService;


    @Override
    public long createOrder(OrderRequest orderRequest) {
        //customer
        this.findCustomerId(orderRequest.customerId());

        //product
       this.productPurchase(orderRequest.products());

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

        return 0;
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
