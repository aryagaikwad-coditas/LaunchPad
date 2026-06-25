package com.example.LaunchPad.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PasswordResetRequest {
    private String newPassword;
    private String oldPassword;
    private String email;
}
