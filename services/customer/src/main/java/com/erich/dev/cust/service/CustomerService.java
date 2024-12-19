package com.erich.dev.cust.service;

import com.erich.dev.cust.dto.request.CustomerRequest;
import com.erich.dev.cust.dto.response.CustomerResponse;

import java.util.List;

public interface CustomerService {

    String createCustomer(CustomerRequest customer);

    String updateCustomer(String id, CustomerRequest customer);

    List<CustomerResponse> findAll();

    boolean exist(String customerId);

    CustomerResponse findById(String id);

    void deleteCustomer(String id);
}
