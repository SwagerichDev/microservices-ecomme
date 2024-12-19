package com.erich.dev.cust.restcontroller;

import com.erich.dev.cust.dto.request.CustomerRequest;
import com.erich.dev.cust.service.CustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/customer")
public class CustomerRestController {


    private final CustomerService customerService;

    public CustomerRestController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    public ResponseEntity<?> createCustomer(@Validated @RequestBody CustomerRequest customer){
        return ResponseEntity.ok(customerService.createCustomer(customer));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCustomer(@PathVariable String id, @Validated @RequestBody CustomerRequest customer){
        return ResponseEntity.ok(customerService.updateCustomer(id, customer));
    }

    @GetMapping
    public ResponseEntity<?> findAll(){
        return ResponseEntity.ok(customerService.findAll());
    }

    @GetMapping("/exist/{customer-id}")
    public ResponseEntity<?> exist(@PathVariable("customer-id") String customerId){
        return ResponseEntity.ok(customerService.exist(customerId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable String id){
        return ResponseEntity.ok(customerService.findById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCustomer(@PathVariable String id){
        customerService.deleteCustomer(id);
        return ResponseEntity.ok("Customer deleted successfully");
    }
}
