package com.example.LaunchPad.entity;

import com.example.LaunchPad.constants.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "users")
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(unique = true,nullable = false)
    @Size(min = 6)
    private String password;

    @Column(nullable = false)
    @Email
    private String email;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Builder.Default
    private boolean is_active = true;
}
