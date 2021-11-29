/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.utils;

import br.com.google.constants.GmailSMTPConstants;
import br.dats.User;
import java.util.Properties;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

/**
 *
 * @author Death
 */
public class EmailVerifyService {

    public static void sendVerificationEmail(String verification, User user) {
        try {
            JavaMailSender sender = new GmailConfig().getJavaMailSender();
            SimpleMailMessage message = new SimpleMailMessage();         
            message.setTo(user.getEmail());
            message.setSubject("Verification Code");
            message.setText("Hello, Your Verification Code is : "+verification);
            sender.send(message);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class GmailConfig {
    
        public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);
 
        mailSender.setUsername(GmailSMTPConstants.EMAIL_ADDRESS);
        mailSender.setPassword(GmailSMTPConstants.APP_PASSWORD);
 
        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");
 
        return mailSender;
    }
}
