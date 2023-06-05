package com.wordle.wordlemania.Model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
public class FriendRequestId implements Serializable {

    @Column(columnDefinition = "int(5)", name = "sender_id")
    private Integer senderId;

    @Column(columnDefinition = "int(5)", name = "receiver_id")
    private Integer receiverId;

}