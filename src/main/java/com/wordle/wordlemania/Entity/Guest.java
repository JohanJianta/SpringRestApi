package com.wordle.wordlemania.Entity;

import java.io.Serializable;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name = "user_guest")
public class Guest implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "int(7)", name = "guest_id")
    private int id;

    @Column(nullable = false, length = 50, name = "guest_name")
    private String name;

    @OneToMany(mappedBy = "guest")
    private Set<GamePlayer> guestPlayer;

    @OneToMany(mappedBy = "guest")
    private Set<History> guestHistory;

    @OneToOne(mappedBy = "userGuest", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private User userData;
}
