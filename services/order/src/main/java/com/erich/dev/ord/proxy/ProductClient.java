package com.erich.dev.ord.proxy;

import com.erich.dev.ord.dto.product.request.ProductPurchaseRequest;
import com.erich.dev.ord.dto.product.response.ProductPurchaseResponse;
import com.erich.dev.ord.dto.product.response.ProductResponse;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@FeignClient(name = "product-service", url = "${application.config.product-url}")
public interface ProductClient {

    @GetMapping("/{product-id}")
    Optional<ProductResponse> getProduct(@PathVariable("product-id") Long id);


    @PostMapping("/pucharse")
    List<ProductPurchaseResponse> pucharseProduct(@Valid @RequestBody List<ProductPurchaseRequest> productRequests);
}
