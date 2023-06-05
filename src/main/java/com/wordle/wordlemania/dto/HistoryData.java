package com.wordle.wordlemania.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class HistoryData {
    private Integer gameCode;

    private Integer roomId;

    private boolean isWin;

    private String word;

    private Date date;

    private Integer score;

    private Integer guessesTry;

    private List<String> playerNames;
    
    private List<Integer> playerIds;
    
    public void addPlayerName(String playerName) {
        if(playerNames == null) {
            playerNames = new ArrayList<String>();
        }
        playerNames.add(playerName);
    }

    public void addPlayerId(Integer playerId) {
        if(playerIds == null) {
            playerIds = new ArrayList<Integer>();
        }
        playerIds.add(playerId);
    }
}