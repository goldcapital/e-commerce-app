package com.example.prodcut.product.service;

import com.example.prodcut.product.dto.request.ProductPurchaseRequest;
import com.example.prodcut.product.dto.request.ProductRequest;
import com.example.prodcut.product.dto.response.ProductPurchaseResponse;
import com.example.prodcut.product.dto.response.ProductResponse;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface ProductService {

    Integer create(ProductRequest request);

    List<ProductPurchaseResponse> purchase(List<ProductPurchaseRequest> requests);

    ProductResponse findById(Integer id);

    List<ProductResponse> findAll(PageRequest pageable);
}
