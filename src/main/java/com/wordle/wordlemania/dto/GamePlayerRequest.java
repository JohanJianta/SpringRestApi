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
public class GamePlayerRequest {
    @NotNull(message = "Player Id is required")
    private Integer playerId;

    @NotNull(message = "Game Id is required")
    private Integer gameId;

    private Integer scoreGain;
}
