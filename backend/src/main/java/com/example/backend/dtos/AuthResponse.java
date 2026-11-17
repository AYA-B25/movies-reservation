package com.example.backend.dtos;

import com.example.backend.entities.Role;

public record AuthResponse(String accessToken, String tokenType, Integer expiresIn, UserSummary user)
{
    public record UserSummary(String lastName, Role role){}

    @Override
    public String toString(){
        return "AuthResponse[accessToken=***, tokenType=" + tokenType
                + ", expiresIn=" + expiresIn
                + ", user=" + user + "]";
    }
}
