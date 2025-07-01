package com.example.ecommerce.order.repository;

import com.example.ecommerce.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Integer> {
}
