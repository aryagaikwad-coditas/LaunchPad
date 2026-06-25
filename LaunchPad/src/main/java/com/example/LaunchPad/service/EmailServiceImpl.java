package com.example.LaunchPad.service;

import com.example.LaunchPad.dto.request.EmailDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl {

    private final JavaMailSender javaMailSender;

    private String sender = "arya.gaikwad@coditas.com";

    public String sendNewHireWelcome(EmailDetails details){
        try{
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(sender);
            message.setTo(details.getRecipient());
            message.setText(details.getMsgBody());
            message.setSubject(details.getSubject());

            javaMailSender.send(message);

            return "Mail send successfully";
        }
        catch (Exception e){
            return "error while sending email to the user";
        }
    }

}
