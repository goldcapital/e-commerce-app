package com.example.ecommerce.service;

import com.example.ecommerce.notiflcation.NotificationProducer;
import com.example.ecommerce.payment.PaymentMapper;
import com.example.ecommerce.payment.enums.PaymentMethod;
import com.example.ecommerce.repository.PaymentRepository;
import com.example.ecommerce.request.PaymentNotificationRequest;
import com.example.ecommerce.request.PaymentRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentMapper paymentMapper;
    private final PaymentRepository paymentRep;
    private final NotificationProducer notificationProducer;

    public Integer createPayment(PaymentRequest request) {
        var payment = paymentRep.save(paymentMapper.toEntity(request));

        notificationProducer.sendNotification(new PaymentNotificationRequest(
                request.orderReference(),
                request.amount(),
                request.paymentMethod(),
                request.customer().firstname(),
                request.customer().lastname(),
                request.customer().email()
        ));
        return payment.getId();
    }
}
