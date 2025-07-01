package com.example.ecommerce.payment;

import com.example.ecommerce.payment.enums.PaymentMethod;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static jakarta.persistence.EnumType.STRING;

@Data
@Builder
@Entity
@Table(name = "payment")
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)

public class Payment {
    @Id
    @GeneratedValue
    private Integer id;
    @Column(name = "amount")
    private BigDecimal amount;
    @Enumerated(STRING)
    @Column(name = "payment_method")
    private PaymentMethod paymentMethod;
    @Column(name = "order_id")
    private Integer orderId;
    @CreatedDate
    @Column(updatable = false, nullable = false, name = "created_data")
    private LocalDateTime createdData;
    @LastModifiedDate
    @Column(insertable = false)
    private LocalDateTime lastModifiedDate;


}
