package com.wordle.wordlemania.Entity;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name = "game_session")
public class Room implements Serializable {
    @Id // penanda primary key untuk kolom
    @GeneratedValue(strategy = GenerationType.IDENTITY) // penanda auto_increment untuk kolom
    @Column(columnDefinition = "int(9)", name = "game_session_id")
    private int id;

    @Column(nullable = false, length = 20)
    private String word;

    @Column(nullable = false, columnDefinition = "int(2)", name = "guesses_try")
    private int guessesTry;

    @Column(nullable = false, columnDefinition = "int(2)", name = "score_to_get")
    private int scorePrize;

    @Column(nullable = false, name = "session_date")
    private Date date;

    @Column(nullable = false, columnDefinition = "tinyint(1) default false", name = "word_guessed")
    private boolean win;

    public Room() {
    }

    public Room(String word, int guessesTry, int scorePrize, boolean win) {
        this.word = word;
        this.guessesTry = guessesTry;
        this.scorePrize = scorePrize;
        this.win = win;
    }

    @PrePersist
    protected void onCreate() {
        date = new Date();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isWin() {
        return win;
    }

    public void setWin(boolean isWin) {
        this.win = isWin;
    }

}
