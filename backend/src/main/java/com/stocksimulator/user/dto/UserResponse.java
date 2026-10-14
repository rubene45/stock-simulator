package com.stocksimulator.user.dto;

import com.stocksimulator.user.model.User;
import com.stocksimulator.user.model.UserRole;

import java.time.Instant;
import java.util.UUID;

public record UserResponse(UUID id, String username, String email, UserRole role, Instant createdAt) {
    public static UserResponse from(User user) {
        return new UserResponse(user.getId(), user.getUsername(), user.getEmail(), user.getRole(), user.getCreatedAt());
    }
}
