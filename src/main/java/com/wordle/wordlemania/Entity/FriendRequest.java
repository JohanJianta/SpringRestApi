package com.wordle.wordlemania.Entity;

import com.wordle.wordlemania.Model.FriendRequestId;
import com.wordle.wordlemania.Model.FriendStatus;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Table(name = "friend_request")
public class FriendRequest {

    @EmbeddedId
    private FriendRequestId id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50, name = "friend_status")
    private FriendStatus status;

    @ManyToOne
    @JoinColumn(name = "sender_id", insertable=false, updatable = false)
    private User sender;

    // @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "receiver_id", insertable=false, updatable = false)
    private User receiver;

}