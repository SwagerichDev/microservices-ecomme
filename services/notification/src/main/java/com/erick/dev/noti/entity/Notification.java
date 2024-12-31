package com.erick.dev.noti.entity;

import com.erick.dev.noti.kafka.order.OrderConfirmation;
import com.erick.dev.noti.kafka.payment.PaymentConfirmation;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document
public class Notification {

    @Id
    private String id;

    private NotificationType notificationType;

    private LocalDateTime notificationDate;

    private OrderConfirmation orderConfirmation;

    private PaymentConfirmation paymentConfirmation;
}
