package com.erich.dev.prod.service.impl;

import com.erich.dev.prod.dto.request.ProductPurchaseRequest;
import com.erich.dev.prod.dto.request.ProductRequest;
import com.erich.dev.prod.dto.response.ProductPurchaseResponse;
import com.erich.dev.prod.dto.response.ProductResponse;
import com.erich.dev.prod.dto.response.ProductResponsePag;
import com.erich.dev.prod.entity.Category;
import com.erich.dev.prod.entity.Product;
import com.erich.dev.prod.exception.NotFoundException;
import com.erich.dev.prod.exception.ProductPurchaseException;
import com.erich.dev.prod.respository.ProductRepository;
import com.erich.dev.prod.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;


    @Override
    @Transactional
    public Long createProduct(ProductRequest productRequest) {
        Product product = toProduct(productRequest);
        return productRepository.save(product).getId();
    }

    @Override
    @Transactional
    public List<ProductPurchaseResponse> productPurchase(List<ProductPurchaseRequest> productRequests) {
        Map<Long, ProductPurchaseRequest> requestMap = productRequests.stream()
                .collect(Collectors.toMap(ProductPurchaseRequest::productId, request -> request));
        List<Product> products = productRepository.findAllByIdInOrderById(requestMap.keySet());
        this.validateProductsExist(requestMap.keySet(), products);
        return this.processProductPurchase(products, requestMap);
    }


    private List<ProductPurchaseResponse> processProductPurchase(
            List<Product> products,
            Map<Long, ProductPurchaseRequest> requestMap) {

        List<ProductPurchaseResponse> response = new ArrayList<>();
        List<Product> productsToUpdate = new ArrayList<>();

        for (Product product : products) {
            var request = requestMap.get(product.getId());
            if (product.getAvailableQuantity() < request.quantity()) {
                throw new ProductPurchaseException("Product " + product.getName() + " has not enough quantity");
            }
            var newQuantity = product.getAvailableQuantity() - request.quantity();
            product.setAvailableQuantity(newQuantity);
            productsToUpdate.add(product);
            response.add(productPurchaseResponse(product, request.quantity()));
        }
        productRepository.saveAll(productsToUpdate);
        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public ProductResponse findProductById(Long productId) {
        return productRepository.findById(productId).map(this::toProductResponse).orElseThrow(
                () -> new NotFoundException("Product not found")
        );
    }

    @Override
    @Transactional(readOnly = true)
    public ProductResponsePag findAllProducts(int page, int size) {
        Pageable pageRequest = PageRequest.of(page, size, Sort.Direction.DESC, "id");
        Page<Product> productsPage = productRepository.findAll(pageRequest);
        List<ProductResponse> productsResponse = productsPage.getContent().stream().map(this::toProductResponse).toList();
        Map<String,Object> pages = Map.of("totalPages", productsPage.getTotalPages(), "totalElements", productsPage.getTotalElements(),"pageNumber",productsPage.getNumber());
        return new ProductResponsePag(productsResponse, pages);
    }

    protected Product toProduct(ProductRequest productRequest) {
        return Product.builder()
                .name(productRequest.name())
                .price(productRequest.price())
                .description(productRequest.description())
                .availableQuantity(productRequest.availableQuantity())
                .category(Category.builder().id(productRequest.categoryId()).build())
                .build();
    }

    protected ProductResponse toProductResponse(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getAvailableQuantity(),
                product.getPrice(),
                product.getCategory().getId(),
                product.getCategory().getName(),
                product.getCategory().getDescription());
    }

    protected ProductPurchaseResponse productPurchaseResponse(Product product, double quantity) {
        return new ProductPurchaseResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                quantity);
    }

    private void validateProductsExist(Set<Long> requestedIds, List<Product> foundProducts) {
        Set<Long> foundIds = foundProducts.stream()
                .map(Product::getId)
                .collect(Collectors.toSet());

        if (!foundIds.containsAll(requestedIds)) {
            Set<Long> missingIds = new HashSet<>(requestedIds);
            missingIds.removeAll(foundIds);
            throw new RuntimeException("Products not found with IDs: " + missingIds);
        }
    }

}
