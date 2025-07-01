package com.example.ecommerce.order.mapper;


import com.example.ecommerce.order.Order;
import com.example.ecommerce.order.request.OrderRequest;
import com.example.ecommerce.order.response.OrderResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    @Mapping(target = "totalAmount",source = "amount")
    @Mapping(target = "id",ignore = true)
    Order toEntity(OrderRequest request);
    @Mapping(target ="amount",source = "totalAmount")
    OrderResponse toDto(Order order);
}
