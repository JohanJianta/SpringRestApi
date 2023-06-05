package com.wordle.wordlemania.Entity;

import java.io.Serializable;

import com.wordle.wordlemania.Model.GamePlayerId;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "gameroom_player")
public class GamePlayer implements Serializable {
    
    @EmbeddedId
    private GamePlayerId id;

    @ManyToOne
    @JoinColumn(name = "game_id", insertable=false, updatable = false)
    private Game game;

    @ManyToOne
    @JoinColumn(name = "guest_id", insertable=false, updatable = false)
    private Guest guest;

}
