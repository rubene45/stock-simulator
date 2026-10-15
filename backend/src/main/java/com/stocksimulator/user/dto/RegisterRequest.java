package com.stocksimulator.user.dto;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.nio.charset.StandardCharsets;

@Getter
@Setter
@NoArgsConstructor
public class RegisterRequest {
    @NotBlank @Size(min = 3, max = 32)
    private String username;

    @NotBlank @Email @Size(max = 254)
    private String email;

    @NotBlank @Size(min = 8, max = 72)
    private String password;

    @AssertTrue(message = "La contraseña excede el límite admitido")
    public boolean isPasswordWithinBcryptLimit() {
        return password == null || password.getBytes(StandardCharsets.UTF_8).length <= 72;
    }
}
