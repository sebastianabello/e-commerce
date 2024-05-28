package com.abello.ecommerce.ecommercereplica.service.registration;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailSenderServiceImp implements IEmailSender{

    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String emailUser;

    @Override
    @Async
    public void send(String to, String email) {
        try {
            MimeMessage mimeMailMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMailMessage,"utf-8");
            helper.setText(email,true);
            helper.setTo(to);
            helper.setSubject("Confirm your email");
            helper.setFrom(emailUser);
            javaMailSender.send(mimeMailMessage);
        }catch (MessagingException e){
            throw new IllegalStateException("Failed to send the email");
        }
    }
}
