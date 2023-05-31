package com.wordle.wordlemania.dto;

import com.wordle.wordlemania.Model.FriendStatus;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FriendRequestData {
    @NotEmpty(message = "Target Id is required")
    private int id;

    private FriendStatus status;

    private String friendName;
}
