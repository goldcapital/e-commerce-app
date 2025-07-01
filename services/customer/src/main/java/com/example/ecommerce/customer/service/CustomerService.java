package com.example.ecommerce.customer.service;

import com.example.ecommerce.customer.config.UserContext;
import com.example.ecommerce.customer.dto.CustomerRequest;
import com.example.ecommerce.customer.dto.CustomerResponse;
import com.example.ecommerce.customer.entity.Customer;
import com.example.ecommerce.customer.mapper.CustomerMapperImpl;
import com.example.ecommerce.customer.repository.CustomerRepository;
import com.example.ecommerce.customer.response.ApiResponse;
import com.example.ecommerce.exp.CustomerNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.ws.rs.ProcessingException;
import java.util.List;

import static com.example.ecommerce.customer.config.ErrorMessage.USER_NOT_FOUND;
import static java.lang.String.format;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerService {
    private final UserContext userContext;
    private final CustomerRepository repository;
    private final KeycloakService keycloakService;
    private final CustomerMapperImpl mapper;

    public String creatCustomer(CustomerRequest request) {
        try {
            var keycloakId = keycloakService.creatKeycloakUser(request);
            return repository.save(mapper.toEntity(request, keycloakId)).getId();
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    public ApiResponse<CustomerResponse> updateCustomer(CustomerRequest request) {

        try {
            var updateUser = updateCustomerInDatabase(request);
            keycloakService.updateKeycloakUser(request);

            var responseDto = mapper.toDto(updateUser);
            return ApiResponse.of(true, "SUCCESS FULLY", responseDto);
        } catch (ProcessingException e) {
            return ApiResponse.of(false, "Unexpected error: " + e.getMessage(), null);

        }
    }


    public List<CustomerResponse> findAllCustomers(PageRequest pageable) {
        log.info("service user name{} user role{} userId {}", userContext.getUsername(), userContext.getRoles(), userContext.getCustomerId());
        return repository.findAll(pageable).stream()
                .map(mapper::toDto)
                .toList();


    }

    public Boolean existsById(String customerId) {
        return repository.existsById(customerId);

    }

    public CustomerResponse getById(String customerId) {
        return repository.findById(customerId)
                .map(mapper::toDto)
                .orElseThrow(() -> new CustomerNotFoundException(format(USER_NOT_FOUND, customerId)));

    }

    public void deleteById(String customerId) {
        repository.findById(customerId).ifPresentOrElse(customer -> {
            repository.deleteById(customerId);
        }, () -> {
            throw new CustomerNotFoundException(format(USER_NOT_FOUND, customerId));
        });
    }

    private Customer updateCustomerInDatabase(CustomerRequest request) {
        return repository.findById(request.id())
                .map(customer -> {
                    log.info("Updating customer in DB: {}", customer);
                    mapper.update(customer, request);
                    return repository.save(customer);
                }).orElseThrow(() -> {
                    log.warn(USER_NOT_FOUND, request.id());
                    return new CustomerNotFoundException(format(USER_NOT_FOUND, request.id()
                    ));
                });
    }
}

