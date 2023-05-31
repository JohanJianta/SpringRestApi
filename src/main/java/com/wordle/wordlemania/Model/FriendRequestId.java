package com.wordle.wordlemania.Model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class FriendRequestId implements Serializable {

    @Column(columnDefinition = "int(5)", name = "sender_id")
    private Integer senderId;

    @Column(columnDefinition = "int(5)", name = "receiver_id")
    private Integer receiverId;

    public Integer getSenderId() {
        return senderId;
    }

    public void setSenderId(Integer userId) {
        this.senderId = userId;
    }

    public Integer getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(Integer friendId) {
        this.receiverId = friendId;
    }

}
