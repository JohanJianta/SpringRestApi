package com.wordle.wordlemania.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.wordle.wordlemania.Model.RoomStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RoomResponseData {
    private Integer gameCode;
    private Integer roomId;
    private String word;
    private Integer guessesTry;
    private Integer scorePrize;
    private Integer length;
    private Boolean win;
    private RoomStatus status;
}
