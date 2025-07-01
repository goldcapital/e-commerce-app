package com.example.ecommerce.kafka;

import com.example.ecommerce.kafka.order.OrderConfirmation;
import com.example.ecommerce.kafka.payment.PaymentConfirmation;
import com.example.ecommerce.notification.Notification;
import com.example.ecommerce.notification.repository.NotificationRepository;
import com.example.ecommerce.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static com.example.ecommerce.notification.enums.NotificationType.ORDER_CONFIRMATION;
import static com.example.ecommerce.notification.enums.NotificationType.PAYMENT_CONFIRMATION;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationConsumer {
    public final NotificationRepository notificationRep;
    private final EmailService emailService;

    @KafkaListener(topics = "payment-topic")
    public void consumerPaymentSuccessNotification(PaymentConfirmation paymentConfirmation) {
        log.info("Consumer the message from payment-topic Topic:: {}", paymentConfirmation.customerEmail());

        notificationRep.save(Notification.builder()
                .type(PAYMENT_CONFIRMATION)
                .notificationData(LocalDateTime.now())
                .paymentConfirmation(paymentConfirmation)
                .build());
        if (paymentConfirmation.customerEmail() == null) {
            log.info("consumer topic Payment customer Email  is null");
            return;
        }
        var customerName = paymentConfirmation.customerFirstname() + "  " + paymentConfirmation.customerLastname();
        emailService.sendPaymentSuccessEmail(
                paymentConfirmation.customerEmail(),
                customerName,
                paymentConfirmation.amount(),
                paymentConfirmation.orderReference()
        );

    }


    @KafkaListener(topics = "order-topic")
    public void consumerOrderSuccessNotification(OrderConfirmation orderConfirmation) {
        log.info("Consumer the message from order-topic Topic:: {}", orderConfirmation);

        notificationRep.save(Notification.builder()
                .type(ORDER_CONFIRMATION)
                .notificationData(LocalDateTime.now())
                .orderConfirmation(orderConfirmation)
                .build());
        if (orderConfirmation.customer() == null) {
            log.info("consumer topic Customer is null");
            return;
        }
        var customerName = orderConfirmation.customer().firstname() + "  " + orderConfirmation.customer().lastname();
        log.info("send email :: {}", orderConfirmation.customer().email());
        emailService.sendOrderConfirmationEmail(
                orderConfirmation.customer().email(),
                customerName,
                orderConfirmation.totalAmount(),
                orderConfirmation.orderReference(),
                orderConfirmation.products()
        );

    }
}
