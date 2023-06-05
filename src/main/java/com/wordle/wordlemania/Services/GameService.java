package com.wordle.wordlemania.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wordle.wordlemania.Entity.Game;
import com.wordle.wordlemania.Model.RoomStatus;
import com.wordle.wordlemania.Repos.GameRepositories;

@Service
public class GameService {
    
    @Autowired
    private GameRepositories gameRepository;

    public Optional<Game> getGameById(int gameId) {
        return gameRepository.findById(gameId);
    }
    
    public Game getRandomGame() {
        List<Game> listGame =  gameRepository.findByStatus(RoomStatus.Public);
        for (Game game : listGame) {
            if(!gameRepository.hasFourPlayers(game)) {
                return game;
            }
        }
        return null;
    }
    
    public Game getPrivateRoom(int id) {
        Game game = gameRepository.findByIdAndStatusOrIdAndStatus(id, RoomStatus.Private, id, RoomStatus.Public);
        if(!gameRepository.hasFourPlayers(game)) {
            return game;
        } else {
            return null;
        }
    }

    public Game createGame(RoomStatus status) {
        Game game = new Game();
        game.setStatus(status);
        return gameRepository.save(game);
    }

    public void finishGame(Game game) {
        game.setStatus(RoomStatus.Finish);
        gameRepository.save(game);
    }
}
