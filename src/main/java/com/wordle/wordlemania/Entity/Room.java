package com.wordle.wordlemania.Entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "room_data")
public class Room implements Serializable {
    @Id // penanda primary key untuk kolom
    @GeneratedValue(strategy = GenerationType.IDENTITY) // penanda auto_increment untuk kolom
    @Column(columnDefinition = "int(9)", name = "room_id")
    private int id;
    
    @Column(nullable = false, columnDefinition = "int(2)", name = "guesses_try")
    private int guessesTry;
    
    @Column(nullable = false, columnDefinition = "int(2)", name = "score_to_get")
    private int scorePrize;
    
    @Column(nullable = false, name = "room_date")
    private Date date;
    
    @Column(nullable = false, columnDefinition = "tinyint(1) default false", name = "word_guessed")
    private boolean win;
    
    @Column(nullable = false, columnDefinition = "int(2)", name = "word_length")
    private int length;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Word word;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Game gameroom;
    
    @OneToMany(mappedBy = "room")
    private Set<History> historyPlayer;

    public Room() {
    }

    public Room(int length, int guessesTry, int scorePrize, boolean win) {
        this.length = length;
        this.guessesTry = guessesTry;
        this.scorePrize = scorePrize;
        this.win = win;
    }

    @PrePersist
    protected void onCreate() {
        date = new Date();
    }

}
