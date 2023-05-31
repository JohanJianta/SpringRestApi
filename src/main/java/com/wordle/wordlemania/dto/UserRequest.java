package com.wordle.wordlemania.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {
    @Size(max = 255)
    private String name;

    @NotEmpty(message = "Email is required")
    @Size(max = 255)
    @Email(message = "Email is not valid")
    private String email;

    @NotEmpty(message = "Password is required")
    @Size(max = 50)
    private String password;
}
