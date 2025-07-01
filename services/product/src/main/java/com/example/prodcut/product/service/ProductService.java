package com.example.prodcut.product.service;

import com.example.prodcut.product.dto.request.ProductPurchaseRequest;
import com.example.prodcut.product.dto.request.ProductRequest;
import com.example.prodcut.product.dto.response.ProductPurchaseResponse;
import com.example.prodcut.product.dto.response.ProductResponse;
import com.example.prodcut.product.exp.EntityNotFoundException;
import com.example.prodcut.product.exp.ProductPurchaseException;
import com.example.prodcut.product.mapper.ProductMapper;
import com.example.prodcut.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static com.example.prodcut.product.dto.message.ErrorMessage.*;
import static java.lang.String.format;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository repository;
    private final ProductMapper productMapper;

    public Integer create(ProductRequest request) {
        return repository.save(productMapper.toEntity(request)).id;
    }

    public List<ProductPurchaseResponse> purchase(List<ProductPurchaseRequest> requests) {
        var productIds = requests
                .stream()
                .map(ProductPurchaseRequest::productId)
                .toList();
        log.info("product purchase products Id {}", productIds);

        var storedProducts = repository.findAllByIdInOrderById(productIds);

        if (productIds.size() != storedProducts.size()) {
            log.warn("productIds {} does not match storedProducts {} ", productIds, storedProducts);
            throw new ProductPurchaseException(ONE_OR_PRODUCT_DOES_NOT_EXISTS);
        }
        var storesRequest = requests
                .stream()
                .sorted(Comparator.comparing(ProductPurchaseRequest::productId))
                .toList();

        var purchaseProducts = new ArrayList<ProductPurchaseResponse>();

        for (int i = 0; i < storedProducts.size(); i++) {
            var product = storedProducts.get(i);
            var productRequest = storesRequest.get(i);

            if (product.getAvailability() < productRequest.quantity()) {
                throw new ProductPurchaseException(format(PRODUCT_IS_NOT_ENOUGH, productRequest.productId()));

            }
            var newAvailableQuantity = product.getAvailability() - productRequest.quantity();
            product.setAvailability(newAvailableQuantity);
            repository.save(product);
            purchaseProducts.add(productMapper.toProductPurchaseResponse(product, productRequest.quantity()));
        }
        return purchaseProducts;
    }

    public ProductResponse findById(Integer id) {
        return repository.findById(id)
                .map(productMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException(format(PRODUCT_NOT_FOUND, id)));
    }

    public List<ProductResponse> findAll(PageRequest pageable) {
        return repository.findAll(pageable)
                .map(productMapper::toDto)
                .toList();
    }
}
