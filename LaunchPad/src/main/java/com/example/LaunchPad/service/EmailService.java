package com.example.LaunchPad.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    @Value("${app.reset-password-url}")
    private String resetPasswordUrl;

    public void sendNewHireWelcomeEmail(String email,
                                        String username,
                                        String tempPassword
                                        ) {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom(fromEmail);
        message.setTo(email);
        message.setSubject("Welcome to LaunchPad : Action Required");
        message.setText(
                """
                        Hi %s,
                        
                        Welcome to Launchpad! Your onboarding journey is ready.
                        
                        Here are your temporary login credentials:
                        
                        Email    : %s
                        Password : %s
                        
                        Please click the link below to set your new password before logging in:
                        
                        %s?email=%s
                        
                        This is a temporary password. You must reset it before you can access the platform.
                        
                        Regards,
                        Launchpad Team""".formatted(username, email, tempPassword, resetPasswordUrl, email)

        );
        mailSender.send(message);
    }
    public void sendOnboardingCompleteEmail(String email, String username) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(email);
        message.setSubject("Congratulations — Onboarding Complete!");
        message.setText(
                """
                Hi %s,

                Congratulations! You have successfully completed your onboarding journey.

                We are thrilled to have you on the team.

                Regards,
                Launchpad Team
                """.formatted(username)
        );
        mailSender.send(message);
    }



    public void sendTaskApprovedEmail(String email, String username, String taskTitle) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(email);
        message.setSubject("Task Approved — " + taskTitle);
        message.setText(
                """
                Hi %s,

                Your task "%s" has been approved by your manager.

                Regards,
                Launchpad Team
                """.formatted(username, taskTitle)
        );
        mailSender.send(message);
    }

    public void sendTaskRejectedEmail(String email, String username,
                                      String taskTitle, String reason) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(email);
        message.setSubject("Task Needs Attention — " + taskTitle);
        message.setText(
                """
                Hi %s,

                Your task "%s" was rejected by your manager.

                Reason: %s

                Please review and resubmit.

                Regards,
                Launchpad Team
                """.formatted(username, taskTitle, reason)
        );
        mailSender.send(message);
    }

    public void sendDocumentApprovedEmail(String email, String username, String fileName) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(email);
        message.setSubject("Document Approved — " + fileName);
        message.setText(
                """
                Hi %s,

                Your document "%s" has been approved by HR.

                Regards,
                Launchpad Team
                """.formatted(username, fileName)
        );
        mailSender.send(message);
    }

    public void sendDocumentRejectedEmail(String email, String username,
                                          String fileName, String reason) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(email);
        message.setSubject("Document Needs Attention — " + fileName);
        message.setText(
                """
                Hi %s,

                Your document "%s" was rejected by HR.

                Reason: %s

                Please resubmit the correct document.

                Regards,
                Launchpad Team
                """.formatted(username, fileName, reason)
        );
        mailSender.send(message);
    }


}