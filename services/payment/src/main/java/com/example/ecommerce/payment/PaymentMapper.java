package com.example.ecommerce.payment;

import com.example.ecommerce.request.PaymentRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PaymentMapper {

Payment toEntity(PaymentRequest request);

}
