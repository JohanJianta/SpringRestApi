package com.wordle.wordlemania.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wordle.wordlemania.Entity.GamePlayer;
import com.wordle.wordlemania.Model.GamePlayerId;
import com.wordle.wordlemania.Repos.GamePlayerRepositories;

@Service
public class GamePlayerService {

    @Autowired
    private GamePlayerRepositories gamePlayerRepository;

    // public Optional<Guest> getGuestById (int guestId) {
    //     return guestRepository.findById(guestId);
    // }

    public boolean isExist (int guestId, int gameId) {
        return gamePlayerRepository.existsByGuestIdAndGameId(guestId, gameId);
    }

    public List<Integer> getAllPlayerIds (int gameId) {
        return gamePlayerRepository.findGuestIdsByGameId(gameId);
    }

    public Boolean savePlayer (int guestId, int gameId) {
        GamePlayerId gamePlayerId = new GamePlayerId(gameId, guestId);
        GamePlayer gamePlayer = new GamePlayer();
        gamePlayer.setId(gamePlayerId);
        gamePlayerRepository.save(gamePlayer);
        return true;
    }

    public Boolean removePlayer (int guestId, int gameId) {
        GamePlayerId gamePlayerId = new GamePlayerId(gameId, guestId);
        gamePlayerRepository.deleteById(gamePlayerId);
        return true;
    }
}
