package com.example.demo.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;

@Service
public class EmailService {
    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String sender;
    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void send(String receiver, String subject, String html){
        MimeMessagePreparator message = mimeMessage -> {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            helper.setFrom(sender + "@gmail.com");
            helper.setTo(receiver);
            helper.setSubject(subject);
            helper.setText(html, true);//default is false, thus will send html code instead
        };
        mailSender.send(message);
    }
}
