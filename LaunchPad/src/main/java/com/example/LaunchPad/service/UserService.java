package com.example.LaunchPad.service;

import com.example.LaunchPad.entity.ConfirmationToken;
import com.example.LaunchPad.entity.Users;
import com.example.LaunchPad.repository.ConfirmationTokenRepository;
import com.example.LaunchPad.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final ConfirmationTokenRepository confirmationTokenRepository;

    private final EmailService emailService;

    public ResponseEntity<?> saveUser(Users user){
        if(userRepository.existsByEmail(user.getEmail())){
            return ResponseEntity.badRequest().body("Email already exists");
        }
        userRepository.save(user);
        ConfirmationToken confirmationToken = new ConfirmationToken();
        confirmationTokenRepository.save(confirmationToken);

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(user.getEmail());
        mailMessage.setSubject("Successfully onboarded ");
        mailMessage.setText("To confirm your account please click here :" + "http://localhost:8080/confirm-account?token=" + confirmationToken.getConfirmationToken());
        emailService.sendMail(mailMessage);
        System.out.println("Confirmation token :" + confirmationToken.getConfirmationToken());
        return ResponseEntity.ok("Verify mail by the link sent to you on your email");
    }


}
