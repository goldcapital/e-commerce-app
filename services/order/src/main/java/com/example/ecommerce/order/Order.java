package com.example.ecommerce.order;

import com.example.ecommerce.order.enums.PaymentMethod;
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
import java.util.List;

import static jakarta.persistence.EnumType.STRING;
@Builder
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "customer_order")
public class Order {
    @Id
    @GeneratedValue
    private Integer id;
    private String reference;
    private BigDecimal totalAmount;
    @Enumerated(STRING)
    private PaymentMethod paymentMethod;
     private String customerId;
     @OneToMany(mappedBy ="order")
     private List<OrderLine>orderLines;
     @CreatedDate
     @Column(updatable = false,nullable = false)
     private LocalDateTime createdAt;
     @LastModifiedDate
    @Column(updatable = false,nullable = false)
    private LocalDateTime lastModifiedDate;
}
