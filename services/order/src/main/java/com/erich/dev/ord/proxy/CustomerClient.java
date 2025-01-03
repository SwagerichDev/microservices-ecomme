package com.erich.dev.ord.proxy;

import com.erich.dev.ord.dto.customer.response.CustomerResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "customer-service", url = "${application.config.customer-url}")
public interface CustomerClient {

    @GetMapping("/{id}")
    CustomerResponse findById(@PathVariable String id);
}
