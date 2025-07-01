package com.example.ecommerce.order.service;

import com.example.ecommerce.order.mapper.OrderLineMapper;
import com.example.ecommerce.order.repository.OrderLineRepository;
import com.example.ecommerce.order.request.OrderLineRequest;
import com.example.ecommerce.order.response.OrderLineResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderLineService {
    private final OrderLineRepository orderLineRepository;
    private final OrderLineMapper orderLineMapper;
    public Integer saveOrderLine(OrderLineRequest orderLineRequest) {
        return orderLineRepository.save(orderLineMapper.toEntity(orderLineRequest)).getId();

    }

    public List<OrderLineResponse> findByOrderId(Integer orderId) {
        return orderLineRepository.findByOrderId(orderId)
                .stream()
                .map(orderLineMapper::toDto)
                .toList();
    }
}
