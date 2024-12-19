package com.erich.dev.cust.service.impl;

import com.erich.dev.cust.dto.request.CustomerRequest;
import com.erich.dev.cust.dto.response.CustomerResponse;
import com.erich.dev.cust.entity.Customer;
import com.erich.dev.cust.exception.NotFoundException;
import com.erich.dev.cust.repository.CustomerRepository;
import com.erich.dev.cust.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerServiceImpl implements CustomerService {


    private final CustomerRepository customerRepository;


    @Override
    public String createCustomer(CustomerRequest customer) {
        log.info("Creating customer: {}", customer);
        var customer1 = Customer.builder()
                .name(customer.firstName())
                .lastName(customer.lastName())
                .email(customer.email())
                .address(customer.address())
                .build();
        customerRepository.save(customer1);
        return "Customer" + customer1.getId() + " created successfully";
    }

    @Override
    public String updateCustomer(String id, CustomerRequest customer) {
        log.info("Updating customer with id: {}", id);
        return customerRepository.findById(id).map(cust -> {
            cust.setName(customer.firstName());
            cust.setLastName(customer.lastName());
            cust.setEmail(customer.email());
            cust.setAddress(customer.address());
            customerRepository.save(cust);
            return "Customer" + cust.getId() + "updated successfully";
        }).orElseThrow(() -> new NotFoundException("Customer not found"));
    }

    @Override
    public List<CustomerResponse> findAll() {
        log.info("Fetching all customers");
        return customerRepository.findAll().stream().map(this::fromCustomer).collect(Collectors.toList());
    }

    @Override
    public boolean exist(String customerId) {
        log.info("Checking if customer with id: {} exists", customerId);
        return customerRepository.findById(customerId).isPresent();
    }

    @Override
    public CustomerResponse findById(String id) {
        log.info("Fetching customer with id: {}", id);
        Optional<Customer> byId = customerRepository.findById(id);
        if (byId.isPresent()) {
            Customer customer = byId.get();
            return fromCustomer(customer);
        }
        throw new NotFoundException("Customer not found");
    }

    @Override
    public void deleteCustomer(String id) {
        log.info("Deleting customer with id: {}", id);
        customerRepository.deleteById(id);
    }

    public CustomerResponse fromCustomer(Customer customer) {
        return new CustomerResponse(
                customer.getName(),
                customer.getLastName(),
                customer.getEmail(),
                customer.getAddress()
        );
    }
}
