package com.wordle.wordlemania.Entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name = "user_data")
public class User implements Serializable {

    @Id // penanda primary key untuk kolom
    @GeneratedValue(strategy = GenerationType.IDENTITY) // penanda auto_increment untuk kolom
    @Column(columnDefinition = "int(5)", name = "user_id")
    private int id;

    @Column(nullable = false, length = 50, name = "user_name")
    private String name;

    @Column(nullable = false, length = 255, name = "user_email", unique = true)
    private String email;

    @Column(nullable = false, columnDefinition = "int(6)", name = "user_score")
    private int score;

    @Column(nullable = false, columnDefinition = "int(9)", name = "total_play")
    private int totalPlay;

    @Column(nullable = false, columnDefinition = "int(9)", name = "total_win")
    private int totalWin;

    @Column(nullable = false, length = 255, name = "hashed_password")
    private String password;

    @Column(nullable = false, length = 255, name = "salt")
    private String salt;

}
