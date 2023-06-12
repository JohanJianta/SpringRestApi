package com.wordle.wordlemania.Repos;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.wordle.wordlemania.Entity.Game;
import com.wordle.wordlemania.Model.RoomStatus;

public interface GameRepositories extends CrudRepository<Game, Integer> {
    public List<Game> findByStatus(RoomStatus status);

    public Game findByIdAndStatusOrIdAndStatus(Integer id1, RoomStatus status1, Integer id2, RoomStatus status2);

    // @Query("SELECT COUNT(gp) = 4 FROM Game g JOIN g.gamePlayer gp WHERE g =
    // :game")
    // Boolean hasFourPlayers(@Param("game") Game game);

    @Query("SELECT COUNT(gp) FROM Game g JOIN g.gamePlayer gp WHERE g = :game")
    Integer getNumberOfPlayers(@Param("game") Game game);

}