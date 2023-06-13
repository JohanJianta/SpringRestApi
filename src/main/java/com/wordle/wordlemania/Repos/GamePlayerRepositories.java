package com.wordle.wordlemania.Repos;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.wordle.wordlemania.Entity.GamePlayer;
import com.wordle.wordlemania.Model.GamePlayerId;

public interface GamePlayerRepositories extends CrudRepository<GamePlayer, GamePlayerId> {
    @Query("SELECT gp.id.guestId FROM GamePlayer gp WHERE gp.id.gameId = :gameId")
    List<Integer> findGuestIdsByGameId(Integer gameId);

    Boolean existsByGuestIdAndGameId (Integer guestId, Integer gameId);

    Integer countByGameId (Integer gameId);
}
