package com.example.LaunchPad.dto.request;

import com.example.LaunchPad.constants.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterRequest {

    @Email
    @NotBlank(message = "Email is a required field ")
    private String email;
    @Size(min = 6)
    @NotBlank(message = "Password is required field")
    private String password;
    @NotBlank(message = "Username is a required field ")
    private String username;
    private Role role;
}
