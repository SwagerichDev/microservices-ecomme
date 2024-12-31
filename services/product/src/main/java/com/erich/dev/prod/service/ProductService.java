package com.erich.dev.prod.service;

import com.erich.dev.prod.dto.request.ProductPurchaseRequest;
import com.erich.dev.prod.dto.request.ProductRequest;
import com.erich.dev.prod.dto.response.ProductPurchaseResponse;
import com.erich.dev.prod.dto.response.ProductResponse;
import com.erich.dev.prod.dto.response.ProductResponsePag;

import java.util.List;

public interface ProductService {


    Long createProduct(ProductRequest productRequest);

    List<ProductPurchaseResponse> productPurchase(List<ProductPurchaseRequest> productRequests);

    ProductResponse findProductById(Long productId);

    ProductResponsePag findAllProducts(int page, int size);
}
