package com.example.LaunchPad.controller;

import com.example.LaunchPad.dto.request.EmailDetails;
import com.example.LaunchPad.service.EmailService;
import com.example.LaunchPad.service.EmailServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/mail")
public class EmailController {
    private final EmailServiceImpl emailServiceImpl;


    @PostMapping("/sendMail")
    @PreAuthorize("hasRole('HR')")
    public String sendMail(
            @RequestBody EmailDetails details) {

        return emailServiceImpl.sendNewHireWelcome(details);
    }
}
