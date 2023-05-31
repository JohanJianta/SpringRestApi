package com.wordle.wordlemania.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserResponseData {
    private Integer id;
    private String name;

    @Size(max = 255)
    @Email(message = "Email is not valid")
    private String email;

    @Size(max = 50)
    private String password;
    
    private Integer score;
    private Integer totalPlay;
    private Integer totalWin;
    
}
