package com.example.ecommerce.order.mapper;

import com.example.ecommerce.order.Order;
import com.example.ecommerce.order.OrderLine;
import com.example.ecommerce.order.request.OrderLineRequest;
import com.example.ecommerce.order.response.OrderLineResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderLineMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "order", expression = "java(mapOrderLine(orderLineRequest.orderId()))")
    OrderLine toEntity(OrderLineRequest orderLineRequest);

    default Order mapOrderLine(Integer orderLineId) {
        if (orderLineId == null) return null;
        return Order.builder()
                .id(orderLineId)
                .build();
    }

    @Mapping(target = "id", source = "id")
    @Mapping(target = "quantity", source = "quantity")
    OrderLineResponse toDto(OrderLine orderLine);
}
