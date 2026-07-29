package com.ratatouille.Ratatouille23.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class UserConfig {

    private final PasswordEncoder passwordEncoder;
    @Bean
    CommandLineRunner userRunner(UserRepository dao) {
        return args -> {
            User armando = new User(
                    "armando",
                    "bausano",
                    "armando@email.com",
                    passwordEncoder.encode("papapapa"),
                    Role.WAITER
            );
            User antonio = new User(
                    "antonio",
                    "d'alterio",
                    "antonio@email.com",
                    passwordEncoder.encode("mrsaxobeat"),
                    Role.COOK
            );
            User carmine = new User(
                    "carmine",
                    "arena",
                    "arena@email.com",
                    passwordEncoder.encode("123"),
                    Role.ADMIN
            );

            dao.saveAll(List.of(armando, antonio, carmine));
        };
    }
}
