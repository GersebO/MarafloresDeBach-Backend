package com.marafloresdebach.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig {
    @Bean
    public PasswordEncoder passwordEncoder() {
        // strength 10 es buen punto de partida; puedes subir a 12 si tu servidor lo tolera
        return new BCryptPasswordEncoder(10);
    }
}
