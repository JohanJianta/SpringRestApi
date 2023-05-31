package com.wordle.wordlemania.dto;

import jakarta.validation.constraints.NotEmpty;

public class RoomRequest {

    @NotEmpty(message = "Word is required")
    private String word;

    @NotEmpty(message = "Guesses try is required")
    private int guessesTry;

    @NotEmpty(message = "Score prize is required")
    private int scorePrize;

    // private boolean win;  // di json isWin dibaca win saja

    // private int idPlayer;

    public void setWord(String word) {
        this.word = word;
    }

    public int getGuessesTry() {
        return guessesTry;
    }

    public void setGuessesTry(int guessesTry) {
        this.guessesTry = guessesTry;
    }

    public int getScorePrize() {
        return scorePrize;
    }

    public void setScorePrize(int scorePrize) {
        this.scorePrize = scorePrize;
    }

    // public boolean isWin() {
    //     return win;
    // }

    // public void setWin(boolean isWin) {
    //     this.win = isWin;
    // }

    public String getWord() {
        return word;
    }

    // public int getIdPlayer() {
    //     return idPlayer;
    // }

    // public void setIdPlayer(int idPlayer) {
    //     this.idPlayer = idPlayer;
    // }
}
