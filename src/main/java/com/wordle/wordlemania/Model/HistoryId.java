package com.wordle.wordlemania.Model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class HistoryId implements Serializable {

    @Column(columnDefinition = "int(9)", name = "game_session_id")
    private Integer roomId;

    @Column(columnDefinition = "int(5)", name = "user_id")
    private Integer userId;

    public Integer getRoomId() {
        return roomId;
    }

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

}
