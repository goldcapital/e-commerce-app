package com.example.ecommerce.service;

import com.example.ecommerce.kafka.order.Product;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.ecommerce.notification.enums.EmailTemplates.ORDER_CONFIRMATION;
import static com.example.ecommerce.notification.enums.EmailTemplates.PAYMENT_CONFIRMATION;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.springframework.mail.javamail.MimeMessageHelper.MULTIPART_MODE_RELATED;

@Slf4j
@Service

public class EmailService {
    @Value("${spring.mail.username}")
    private String fromAccount;
    private final JavaMailSender javaMailSender;
    private final SpringTemplateEngine templateEngine;

    public EmailService(JavaMailSender javaMailSender, SpringTemplateEngine templateEngine) {
        this.javaMailSender = javaMailSender;
        this.templateEngine = templateEngine;
    }

    @Async
    public void sendPaymentSuccessEmail(
            String destinationEmail,
            String customerName,
            BigDecimal amount,
            String orderReference
    ) {
        MimeMessage message = javaMailSender.createMimeMessage();
        try {
            log.info("Attempting to send payment confirmation email to {}", destinationEmail);

            var mimeMessageHelper = new MimeMessageHelper(message, MULTIPART_MODE_RELATED, UTF_8.name());

            mimeMessageHelper.setFrom(fromAccount);
            final var templateName = PAYMENT_CONFIRMATION.getTemplate();

            var variables = new HashMap<String, Object>();
            variables.put("customerName", customerName);
            variables.put("amount", amount);
            variables.put("orderReference", orderReference);

            var context = new Context();
            context.setVariables(variables);
            mimeMessageHelper.setSubject(PAYMENT_CONFIRMATION.getSubject());

            var htlTemplate = templateEngine.process(templateName, context);
            mimeMessageHelper.setText(htlTemplate, true);
            mimeMessageHelper.setTo(destinationEmail);
            javaMailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    @Async
    public void sendOrderConfirmationEmail(
            String destinationEmail,
            String customerName,
            BigDecimal amount,
            String orderReference,
            List<Product> products
    ) {
        log.info("Attempting to send order confirmation email to {}", destinationEmail);

        MimeMessage message = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper mimeMessageHelper =
                    new MimeMessageHelper(message, MULTIPART_MODE_RELATED, UTF_8.name());
            mimeMessageHelper.setFrom(fromAccount);
            final String templateName = ORDER_CONFIRMATION.getTemplate();
            Map<String, Object> variables = new HashMap<>();
            variables.put("customerName", customerName);
            variables.put("totalAmount", amount);
            variables.put("orderReference", orderReference);
            variables.put("products", products);

            Context context = new Context();
            context.setVariables(variables);
            mimeMessageHelper.setSubject(ORDER_CONFIRMATION.getSubject());

            String htlTemplate = templateEngine.process(templateName, context);
            mimeMessageHelper.setText(htlTemplate, true);
            mimeMessageHelper.setTo(destinationEmail);
            javaMailSender.send(message);
            log.info("INFO -Email successfully sent to {} with template {}", destinationEmail, htlTemplate);
        } catch (MessagingException e) {
            log.warn("WARNING -Cannot send email to {}", destinationEmail);

            throw new RuntimeException(e);
        }
    }
}
