package com.stocksimulator.user.service;

import com.stocksimulator.user.dto.RegisterRequest;
import com.stocksimulator.user.dto.UserDto;
import com.stocksimulator.user.model.User;
import com.stocksimulator.user.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Locale;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public UserDto register(RegisterRequest request) {
        String username = request.getUsername().trim();
        String email = request.getEmail().trim().toLowerCase(Locale.ROOT);

        if (userRepository.existsByUsernameIgnoreCase(username)) {
            throw new UserAlreadyExistsException("Ese nombre de usuario ya está en uso.");
        }
        if (userRepository.existsByEmailIgnoreCase(email)) {
            throw new UserAlreadyExistsException("Ese correo electrónico ya está registrado.");
        }

        User user = new User(username, email, passwordEncoder.encode(request.getPassword()));
        return UserDto.from(userRepository.save(user));
    }
}
