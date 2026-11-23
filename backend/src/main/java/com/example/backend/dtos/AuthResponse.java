package com.example.backend.dtos;

public record AuthResponse(String accessToken, String tokenType, Long expiresIn, UserResponse user)
{

    @Override
    public String toString(){
        return "AuthResponse[accessToken=***, tokenType=" + tokenType
                + ", expiresIn=" + expiresIn
                + ", user=" + user + "]";
    }
}
