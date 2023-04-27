package com.example.service;

import com.example.entity.EmailEntity;
import com.example.repository.EmailRepository;
import com.example.util.JwtUtil;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class MailSenderService {
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private EmailRepository emailRepository;
    @Value("${spring.mail.username}")
    private String fromAccount;
    @Value("${server.host}")
    private String serverHost;

    public void sendRegistrationEmail(String toAccount) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Registration verification.\n");
        stringBuilder.append("Click to below link to complete registration\n");
        stringBuilder.append("Link: ");
        stringBuilder.append(serverHost).append("/api/v1/auth/verification/");
        stringBuilder.append(JwtUtil.encode(toAccount));
        // https://kun.uz/api/v1/auth//email/verification/dasdasdasd.asdasdad.asda
        // localhost:8080/api/v1/auth/email/verification/dasdasdasd.asdasdad.asda
        sendEmail(toAccount, "Registration", stringBuilder.toString());
        EmailEntity email = new EmailEntity();
        email.setEmail(toAccount);
        email.setMessage(stringBuilder.toString());
        email.setCreatedData(LocalDateTime.now());
        emailRepository.save(email);
    }

    public void sendRegistrationEmailMime(String toAccount) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<h1 style=\"text-align: center\">Registration verification</h1>");
        stringBuilder.append("<br><br>");
        // <p><a href="asd.dasdad.asdaasda">Click to the link to complete registration</a></p>
        stringBuilder.append("<p><a href=\"");
        stringBuilder.append(serverHost).append("/api/v1/auth/email/verification/");
        stringBuilder.append(JwtUtil.encode(toAccount)).append("\">");
        stringBuilder.append("Click to the link to complete registration</a></p>");
        sendEmailMime(toAccount, "Registration", stringBuilder.toString());
    }

    private void sendEmail(String toAccount, String subject, String text) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom(fromAccount);
        msg.setTo(toAccount);
        msg.setSubject(subject);
        msg.setText(text);
        javaMailSender.send(msg);
    }

    private void sendEmailMime(String toAccount, String subject, String text) {
        MimeMessage msg = javaMailSender.createMimeMessage();
        try {
            msg.setFrom(fromAccount);
            MimeMessageHelper helper = new MimeMessageHelper(msg, true);
            helper.setTo(toAccount);
            helper.setSubject(subject);
            helper.setText(text, true);
            javaMailSender.send(msg);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
