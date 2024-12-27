package com.example.antivirusbackend.controller;

import com.example.antivirusbackend.entity.User;
import com.example.antivirusbackend.repository.UserRepository;
import com.example.antivirusbackend.security.JwtTokenProvider;
import org.springframework.web.bind.annotation.*;

//TODO: Неверная структура проекта. Пакеты должны начинаться с ru.mtuci.имя_проекта

//TODO: 1. Почему чувствительные данные передаются в параметрах запроса?
//TODO: 2. login - почему нельзя сразу найти пользователя в БД по логину? Если их будет миллион, вы всех в память будете загружать?
//TODO: 3. register - зачем дважды загружать в память всю таблицу пользователей?
//TODO: 4. Пароль должен хэшироваться при регистрации
//TODO: 5. Общее для всех контроллеров. Общаться при помощи сущностей БД - плохая практика

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthController(UserRepository userRepository, JwtTokenProvider jwtTokenProvider) {
        this.userRepository = userRepository;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping("/login")
    public String login(@RequestParam("login") String login,
                        @RequestParam("password") String password) {
        User user = userRepository.findAll().stream()
                .filter(u -> u.getLogin().equals(login))
                .findFirst()
                .orElse(null);

        if (user == null || !user.getPasswordHash().equals(password)) {
            throw new RuntimeException("Invalid login or password");
        }

        String token = jwtTokenProvider.createToken(user.getLogin(), user.getRole());
        return token;
    }

    @PostMapping("/register")
    public User register(@RequestBody User newUser) {
        // Можно добавить проверки и хеширование пароля
        // Например, проверить, что логин и email уникальны

        if (userRepository.findAll().stream().anyMatch(u -> u.getLogin().equals(newUser.getLogin()))) {
            throw new RuntimeException("User with this login already exists");
        }
        if (userRepository.findAll().stream().anyMatch(u -> u.getEmail().equals(newUser.getEmail()))) {
            throw new RuntimeException("User with this email already exists");
        }

        // Пока без шифрования пароля, хотя в реальном проекте passwordHash должен быть хеширован
        return userRepository.save(newUser);
    }
}
