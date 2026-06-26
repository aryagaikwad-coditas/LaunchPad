package com.example.LaunchPad.config;

import com.example.LaunchPad.constants.Role;
import com.example.LaunchPad.entity.Users;
import com.example.LaunchPad.repository.UserRepository;
import jakarta.persistence.Column;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        seedUser("HR","hrlaunchpad@gmail.com","hr123", Role.HR);
    }

    private void seedUser(String username, String email, String password, Role role) {
        if(!userRepository.existsByEmail(email)){
            Users user = new Users();
            user.setEmail(email);
            user.setPassword(passwordEncoder.encode(password));
            user.setRole(role);
            user.setUsername(username);
            user.setPasswordChanged(true);
            userRepository.save(user);
            System.out.println("Seeded Admin :" + email);
        }
    }
}
