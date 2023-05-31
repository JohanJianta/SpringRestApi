package com.wordle.wordlemania.Entity;

import com.wordle.wordlemania.Model.HistoryId;

import jakarta.persistence.Column;
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
@Table(name = "game_session_player")
public class History {

    @EmbeddedId
    private HistoryId id;

    // @Enumerated(EnumType.STRING)
    // @Column(nullable = false, length = 50, name = "user_role")
    // private UserRole role;

    @Column(nullable = false, columnDefinition = "tinyint(1) default true", name = "showable")
    private boolean showable;

    @ManyToOne
    @JoinColumn(name = "game_session_id", insertable=false, updatable = false)
    private Room room;

    @ManyToOne
    @JoinColumn(name = "user_id", insertable=false, updatable = false)
    private User user;
}
