package com.example.ecommerce.order.repository;

import com.example.ecommerce.order.OrderLine;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderLineRepository extends JpaRepository<OrderLine, Integer> {

    List<OrderLine> findByOrderId(Integer orderId);
}
