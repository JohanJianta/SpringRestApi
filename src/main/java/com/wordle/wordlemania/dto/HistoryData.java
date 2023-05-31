package com.wordle.wordlemania.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HistoryData {
    private int id;

    private boolean isWin;

    private String word;

    private Date date;

    private int score;

    private List<String> playerNames = new ArrayList<String>();

    public void addPlayer(String playerName) {
        playerNames.add(playerName);
    }
}
