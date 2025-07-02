package com.example.prodcut.product.controller;

import com.example.prodcut.product.dto.request.ProductRequest;
import com.example.prodcut.product.dto.request.ProductPurchaseRequest;
import com.example.prodcut.product.dto.response.ProductPurchaseResponse;
import com.example.prodcut.product.dto.response.ProductResponse;
import com.example.prodcut.product.service.ProductService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static org.springframework.data.domain.PageRequest.of;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/product")
public class ProductController {
    private final ProductService productService;

    @PostMapping(value = "/create",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Integer> createProduct(@RequestBody @Valid ProductRequest request,
                                                 @RequestParam("image")MultipartFile multipartFile) {
        return ResponseEntity.ok(productService.create(request,multipartFile));
    }

    @PostMapping("/purchase")
    public ResponseEntity<List<ProductPurchaseResponse>> purchaseProducts(@RequestBody List<ProductPurchaseRequest> requests
                                                                         ) {
        log.info("product purchase requests: {}", requests);
        return ResponseEntity.ok(productService.purchase(requests));


    }


    @GetMapping("/{product-id}")
    public ResponseEntity<ProductResponse> findById(@PathVariable("product-id") Integer id) {
        return ResponseEntity.ok(productService.findById(id));
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<ProductResponse>> findAll(
            @Min(value = 1, message = "number.min.value.1")
            @RequestParam(defaultValue = "1") Integer page,
            @Min(value = 10, message = "number.min.value.10")
            @RequestParam(defaultValue = "10") Integer size) {
        //   return ResponseEntity.ok(service.findAll(of(page-1,size,Sort.by("createAt").descending())));
        return ResponseEntity.ok(productService.findAll(of(page - 1, size)));

    }
}
