package com.stocksimulator.user.service;

import com.stocksimulator.user.dto.LoginRequest;
import com.stocksimulator.user.dto.UserDto;
import com.stocksimulator.user.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final SecurityContextRepository securityContextRepository;
    private final UserRepository userRepository;

    public AuthService(AuthenticationManager authenticationManager,
                       SecurityContextRepository securityContextRepository,
                       UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.securityContextRepository = securityContextRepository;
        this.userRepository = userRepository;
    }

    public UserDto login(LoginRequest request,
                         HttpServletRequest httpRequest,
                         HttpServletResponse httpResponse) {
        // Spring valida las credenciales y devuelve la autenticación resultante.
        Authentication authentication = authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken.unauthenticated(
                        request.getEmail().trim(),
                        request.getPassword()));

        // Guardamos la autenticación en la sesión para reutilizarla mediante JSESSIONID.
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);
        securityContextRepository.saveContext(context, httpRequest, httpResponse);

        return userRepository.findByEmailIgnoreCase(request.getEmail().trim())
                .map(UserDto::from)
                .orElseThrow();
    }

    public UserDto getCurrentUser(Authentication authentication) {
        return userRepository.findByEmailIgnoreCase(authentication.getName())
                .map(UserDto::from)
                .orElseThrow();
    }

    public void logout(HttpServletRequest httpRequest,
                       HttpServletResponse httpResponse,
                       Authentication authentication) {
        // Invalida la sesión y elimina la autenticación del contexto de seguridad.
        new SecurityContextLogoutHandler().logout(httpRequest, httpResponse, authentication);
    }
}
