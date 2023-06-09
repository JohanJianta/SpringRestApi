package com.wordle.wordlemania.Model;

import java.io.Serializable;

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
public class HistoryId implements Serializable {

    @Column(columnDefinition = "int(9)", name = "room_id")
    private Integer roomId;

    @Column(columnDefinition = "int(7)", name = "guest_id")
    private Integer guestId;

}
