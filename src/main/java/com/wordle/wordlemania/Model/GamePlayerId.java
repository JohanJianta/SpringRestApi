package com.wordle.wordlemania.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class GamePlayerId {
    
    @Column(columnDefinition = "int(7)", name = "game_id")
    private Integer gameId;

    @Column(columnDefinition = "int(7)", name = "guest_id")
    private Integer guestId;

}
