package com.wordle.wordlemania.Entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.Set;

import com.wordle.wordlemania.Model.RoomStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "gameroom")
public class Game implements Serializable {
    @Id // penanda primary key untuk kolom
    @GeneratedValue(strategy = GenerationType.IDENTITY) // penanda auto_increment untuk kolom
    @Column(columnDefinition = "int(7)")
    private int id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50, name = "gameroom_status")
    private RoomStatus status;

    @OneToMany(mappedBy = "gameroom")
    private Collection<Room> rooms;

    @OneToMany(mappedBy = "game")
    private Set<GamePlayer> gamePlayer;
}
