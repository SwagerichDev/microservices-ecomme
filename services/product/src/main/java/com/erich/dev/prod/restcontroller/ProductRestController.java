package com.erich.dev.prod.restcontroller;

import com.erich.dev.prod.dto.request.ProductRequest;
import com.erich.dev.prod.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
public class ProductRestController {

    private final ProductService productService;

    public ProductRestController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<?> createProduct(@Valid @RequestBody ProductRequest productRequest) {
        return ResponseEntity.ok(productService.createProduct(productRequest));
    }

    @PostMapping("/pucharse")
    public ResponseEntity<?> pucharseProduct(@Valid @RequestBody List<ProductRequest> productRequest) {
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{product-id}")
    public ResponseEntity<?> getProduct(@PathVariable("product-id") Long id) {
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<?> findAllPagination(@RequestParam(value = "page", defaultValue = "0") Integer page,
                                               @RequestParam(value = "size", defaultValue = "10") Integer size) {
        return ResponseEntity.ok().build();
    }
}
