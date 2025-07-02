package com.example.prodcut.product.service.impl;

import com.example.prodcut.product.dto.request.ProductPurchaseRequest;
import com.example.prodcut.product.dto.request.ProductRequest;
import com.example.prodcut.product.dto.response.ProductPurchaseResponse;
import com.example.prodcut.product.dto.response.ProductResponse;
import com.example.prodcut.product.exp.EntityNotFoundException;
import com.example.prodcut.product.exp.ProductPurchaseException;
import com.example.prodcut.product.mapper.ProductMapper;
import com.example.prodcut.product.repository.ProductRepository;
import com.example.prodcut.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

import static com.example.prodcut.product.dto.message.ErrorMessage.*;
import static java.lang.String.format;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository repository;
    private final ProductMapper productMapper;

    public Integer create(ProductRequest request, MultipartFile multipartFile) {

        return repository.save(productMapper.toEntity(request)).id;
    }

    public List<ProductPurchaseResponse> purchase(List<ProductPurchaseRequest> requests) {
        var purchaseProducts = new ArrayList<ProductPurchaseResponse>();

        requests.forEach(request -> {
            repository.findByIdAndColor(request.productId(), request.productColor())
                    .filter(product -> product.getAvailability() >= request.quantity())
                    .map(product -> {
                        var newAvailableQuantity = product.getAvailability() - request.quantity();
                        product.setAvailability(newAvailableQuantity);
                        repository.save(product);
                        return product;
                    }).map(product -> productMapper.toProductPurchaseResponse(product, request))
                    .ifPresent(purchaseProducts::add);

        });
        if (requests.size() != purchaseProducts.size()) {
            log.warn("productIds {} does not match storedProducts {} ", requests, purchaseProducts);
            throw new ProductPurchaseException(ONE_OR_PRODUCT_DOES_NOT_EXISTS);
        }
        return purchaseProducts;
    }

    @Override
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
