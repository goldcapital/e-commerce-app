package com.example.prodcut.product.repository;

import com.example.prodcut.product.Product;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface ProductRepository extends JpaRepository<Product,Integer> {
    List<Product> findAllByIdInOrderById(List<Integer> productIds);

    Optional<Product> findByIdAndColor (Integer integer, String s);
}
