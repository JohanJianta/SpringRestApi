package com.wordle.wordlemania.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HistoryRequest {
    @NotNull(message = "User Id must is required")
    private int userId;

    @NotNull(message = "Room Id is required")
    private int roomId;
}
