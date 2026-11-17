package com.example.backend.dtos;

import com.example.backend.entities.Role;

public record UserResponse(Long id, String firstName, String lastName, String email, Role role) {
}
