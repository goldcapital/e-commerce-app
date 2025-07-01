package com.example.ecommerce.order.service;

import com.example.ecommerce.order.Order;
import com.example.ecommerce.order.config.kafka.OrderConfirmation;
import com.example.ecommerce.order.config.kafka.OrderProducer;
import com.example.ecommerce.order.customer.CustomerClient;
import com.example.ecommerce.order.exp.BusinessException;
import com.example.ecommerce.order.mapper.OrderMapper;
import com.example.ecommerce.order.page.PageResponse;
import com.example.ecommerce.product.ProductServiceClient;
import com.example.ecommerce.order.repository.OrderRepository;
import com.example.ecommerce.order.request.OrderLineRequest;
import com.example.ecommerce.order.request.OrderRequest;
import com.example.ecommerce.order.request.PurchaseRequest;
import com.example.ecommerce.order.response.OrderResponse;
import com.example.ecommerce.payment.PaymentClient;
import com.example.ecommerce.payment.PaymentRequest;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.lang.String.format;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final CustomerClient customerClient;
    private final ProductServiceClient productClient;
    private final OrderMapper orderMapper;
    private final OrderLineService orderLineService;
    private final OrderProducer orderProducer;
    private final PaymentClient paymentClient;

    public Integer createdOrder(OrderRequest request) {
        var customer = this.customerClient.findCustomerById(request.customerId())
                .orElseThrow(() -> new BusinessException("Cannot create order:: NO Customer exists with the provided ID"));
        log.info("--- Order from customer --- {}", customer);
        var purchasedProducts = productClient.purchaseProducts(request.purchase());
        log.info("Purchase products from customer --- {}", purchasedProducts);

        var order = orderRepository.save(orderMapper.toEntity(request));

        for (PurchaseRequest purchaseRequest : request.purchase()) {
            orderLineService.saveOrderLine(new OrderLineRequest(null, order.getId(),
                            purchaseRequest.productId(),
                            purchaseRequest.quantity()));
        }
        var paymentRequest = new PaymentRequest(
                request.amount(),
                request.paymentMethod(),
                order.getId(),
                order.getReference(),
                customer
        );
        paymentClient.requestOrderPayment(paymentRequest);
        log.info("Order created kafka request reference -{} request amount {} payment method {} --- customer {}",request.reference(),request.amount(),request.paymentMethod(),customer);
        orderProducer.sendOrderConfirmation(
                new OrderConfirmation(
                        request.reference(),
                        request.amount(),
                        request.paymentMethod(),
                        customer,
                        purchasedProducts
                ));
        return order.getId();
    }

    public PageResponse<OrderResponse> findAll(PageRequest pageable) {
        Page<Order> orderPage = orderRepository.findAll(pageable);

        List<OrderResponse> content = orderPage.map(orderMapper::toDto).getContent();
        return new PageResponse<>(content, orderPage.getTotalPages(), orderPage.getTotalElements(), orderPage.getSize(), orderPage.getNumber() + 1);
    }

    public OrderResponse findById(Integer orderId) {
        return orderRepository.findById(orderId)
                .map(orderMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException(format("NO order found with ID: %d", orderId)));
    }
}
