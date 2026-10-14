package com.stocksimulator.user.service;

import com.stocksimulator.user.dto.RegisterRequest;
import com.stocksimulator.user.dto.UserResponse;
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
    public UserResponse register(RegisterRequest request) {
        String username = request.username().trim();
        String email = request.email().trim().toLowerCase(Locale.ROOT);

        if (userRepository.existsByUsernameIgnoreCase(username)) {
            throw new UserAlreadyExistsException("Ese nombre de usuario ya está en uso.");
        }
        if (userRepository.existsByEmailIgnoreCase(email)) {
            throw new UserAlreadyExistsException("Ese correo electrónico ya está registrado.");
        }

        User user = new User(username, email, passwordEncoder.encode(request.password()));
        return UserResponse.from(userRepository.save(user));
    }
}
