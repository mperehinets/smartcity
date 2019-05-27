package com.smartcity.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

@Service
public class EmailServiceImpl implements EmailService {
    private final JavaMailSender mailSender;
    private static final Logger logger = LoggerFactory.getLogger(EmailServiceImpl.class);


    @Value("${spring.mail.username}")
    private String username;

    @Autowired
    public EmailServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void sendSimpleMessage(String subject, String text, String... emailTo) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(from());
        mailMessage.setTo(emailTo);
        mailMessage.setSubject(subject);
        mailMessage.setText(text);
        mailSender.send(mailMessage);
    }

    @Override
    public void sendMessageWithAttachment(String subject, String text, File[] attachments, String... emailTo) {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(emailTo);
            helper.setFrom(from());
            helper.setSubject(subject);
            helper.setText(text);
            for (File file : attachments) {
                FileSystemResource fr = new FileSystemResource(file);
                helper.addAttachment(file.getName(), fr);
            }
            mailSender.send(message);
        } catch (MessagingException e) {
            logger.error("Can't send message with attachment.Error:{}", e.getMessage());
        }
    }

    private String from() {
        return String.format("Smartcity Notificator <%s>", username);
    }
}
