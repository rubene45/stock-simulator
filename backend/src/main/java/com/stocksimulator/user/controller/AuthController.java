package com.stocksimulator.user.controller;

import com.stocksimulator.user.dto.RegisterRequest;
import com.stocksimulator.user.dto.LoginRequest;
import com.stocksimulator.user.dto.UserDto;
import com.stocksimulator.user.service.AuthService;
import com.stocksimulator.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.web.csrf.CsrfToken;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final UserService userService;
    private final AuthService authService;

    public AuthController(UserService userService,
                          AuthService authService) {
        this.userService = userService;
        this.authService = authService;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto register(@Valid @RequestBody RegisterRequest request) {
        return userService.register(request);
    }

    @PostMapping("/login")
    public UserDto login(@Valid @RequestBody LoginRequest request,
                              HttpServletRequest httpRequest,
                              HttpServletResponse httpResponse) {
        return authService.login(request, httpRequest, httpResponse);
    }

    @GetMapping("/me")
    public UserDto me(Authentication authentication) {
        return authService.getCurrentUser(authentication);
    }

    @PostMapping("/logout")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void logout(HttpServletRequest httpRequest,
                       HttpServletResponse httpResponse,
                       Authentication authentication) {
        authService.logout(httpRequest, httpResponse, authentication);
    }

    @GetMapping("/csrf")
    public Map<String, String> csrf(CsrfToken csrfToken) {
        return Map.of("token", csrfToken.getToken());
    }
}
