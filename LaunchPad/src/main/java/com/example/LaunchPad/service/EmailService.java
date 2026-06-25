package com.example.LaunchPad.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service("emailService")
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender javaMailSender;

    public void sendNewHireWelcomeMail(String email,String password){
        SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setTo(email);
        mailMessage.setSubject("Welcome Onboard ");
        mailMessage.setText("""
                Welcome To LaunchPad
                
                Please find the credentials for logging in below :
                
                Email :%s
                
                Password : %s
                
                We are pleased to have you on board 
                
                Regards Team LaunchPAD
                """
                .formatted(email,password)
        );
        javaMailSender.send(mailMessage);
    }
}
