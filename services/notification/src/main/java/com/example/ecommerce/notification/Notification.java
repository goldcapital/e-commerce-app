package com.example.ecommerce.notification;

import com.example.ecommerce.kafka.order.OrderConfirmation;
import com.example.ecommerce.kafka.payment.PaymentConfirmation;
import com.example.ecommerce.notification.enums.NotificationType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Builder
@Document
@AllArgsConstructor
@NoArgsConstructor
public class Notification {
@Id
    private String id;
    private NotificationType type;
    private LocalDateTime notificationData;
    private OrderConfirmation orderConfirmation;
    private PaymentConfirmation paymentConfirmation;
}
