package com.stocksimulator.user.dto;

import com.stocksimulator.user.model.User;
import com.stocksimulator.user.model.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Instant;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class UserDto {

    private UUID id;
    private String username;
    private String email;
    private UserRole role;
    private Instant createdAt;

    public static UserDto from(User user) {
        return new UserDto(user.getId(), user.getUsername(), user.getEmail(), user.getRole(), user.getCreatedAt());
    }
}
