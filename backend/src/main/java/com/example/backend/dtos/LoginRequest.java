package com.example.backend.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginRequest(@NotBlank @Email String email, @NotBlank @Size(max = 72)String password) {
    @Override
    public String toString(){
        return "LoginRequest [Email : "+email+" - Password : ***]";
    }
}
