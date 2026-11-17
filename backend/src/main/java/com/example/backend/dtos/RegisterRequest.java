package com.example.backend.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record RegisterRequest(@NotBlank @Size(min = 2, max=100) String firstName, @NotBlank @Size(min = 2, max=100)
    String lastName, @NotBlank @Email @Size(max=100) String email, @NotBlank @Size(min=8, max=72)
            @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d).+$", message = "Password must contain at least one letter and one number.") String password
) {
    @Override
    public String toString(){
        return "RegisterRequest [First Name : "+firstName+ " - Last Name : "+ lastName+" - Email : "+email+
                " - Password : ***]";
    }
}
