package com.example.ecommerce.customer.mapper;

import com.example.ecommerce.customer.dto.CustomerRequest;
import com.example.ecommerce.customer.entity.Customer;
import com.example.ecommerce.customer.dto.CustomerResponse;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
    @Mapping(target = "keycloakId", source = "id")
    Customer toEntity(CustomerRequest request, String id);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void update(@MappingTarget Customer customer, CustomerRequest request);


    CustomerResponse toDto(Customer customer);
}
