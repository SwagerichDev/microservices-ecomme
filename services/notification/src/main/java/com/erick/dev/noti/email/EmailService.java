package com.erick.dev.noti.email;

import com.erick.dev.noti.kafka.order.Product;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {

    private final JavaMailSender mailSender;

    private final SpringTemplateEngine templateEngine;

    private static final String SENDER_EMAIL = "erickhc.social@gmail.com";



    @Async
    public void sendTemplatedEmail(EmailDetails emailDetails) {
        try {
            MimeMessage message = createMimeMessage(emailDetails);
            mailSender.send(message);
            log.info("Email sent successfully to: {}", emailDetails.getDestinationEmail());
        } catch (MessagingException e) {
            log.error("Failed to send email to: {}", emailDetails.getDestinationEmail(), e);
            throw new RuntimeException("Failed to send email", e);
        }
    }


    private MimeMessage createMimeMessage(EmailDetails emailDetails) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(message,
                MimeMessageHelper.MULTIPART_MODE_RELATED,
                StandardCharsets.UTF_8.name());

        messageHelper.setFrom(SENDER_EMAIL);
        messageHelper.setTo(emailDetails.getDestinationEmail());
        messageHelper.setSubject(emailDetails.getTemplate().getSubject());

        String html = processTemplate(emailDetails);
        messageHelper.setText(html, true);

        return message;
    }

    private String processTemplate(EmailDetails emailDetails) {
        Context context = new Context();
        context.setVariables(emailDetails.createTemplateModel());
        return templateEngine.process(emailDetails.getTemplate().getTemplate(), context);
    }


    @Async
    public void sendPaymentSuccessEmail(String destinationEmail, String customerName, BigDecimal amount, String orderReference) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();

        MimeMessageHelper messageHelper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_RELATED, StandardCharsets.UTF_8.name());

        messageHelper.setFrom(SENDER_EMAIL);
        final String template = EmailTemplate.PAYMENT_CONFIRMATION.getTemplate();

        Map<String, Object> model = Map.of("customerName", customerName, "amount", amount, "orderReference", orderReference);

        Context context = new Context();
        context.setVariables(model);
        messageHelper.setSubject(EmailTemplate.PAYMENT_CONFIRMATION.getSubject());

        try{
            String html = templateEngine.process(template, context);
            messageHelper.setText(html, true);
            messageHelper.setTo(destinationEmail);
            mailSender.send(message);
            log.info("Email sent successfully to: {}", destinationEmail);
        } catch (MessagingException e) {
            log.error("Failed to send email", e);
            e.printStackTrace();
        }
    }


    @Async
    public void sendOrderConfirmationEmail(String destinationEmail, String customerName, BigDecimal amount, String orderReference, List<Product> products) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();

        MimeMessageHelper messageHelper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_RELATED, StandardCharsets.UTF_8.name());

        messageHelper.setFrom(SENDER_EMAIL);
        final String template = EmailTemplate.ORDER_CONFIRMATION.getTemplate();

        Map<String, Object> model = Map.of("customerName", customerName, "totalAmount", amount, "orderReference", orderReference, "products", products);


        Context context = new Context();
        context.setVariables(model);
        messageHelper.setSubject(EmailTemplate.ORDER_CONFIRMATION.getSubject());

        try{
            String html = templateEngine.process(template, context);
            messageHelper.setText(html, true);
            messageHelper.setTo(destinationEmail);
            mailSender.send(message);
            log.info("Email sent successfully to: {}", destinationEmail);
        } catch (MessagingException e) {
            log.error("Failed to send email", e);
            e.printStackTrace();
        }
    }

}
