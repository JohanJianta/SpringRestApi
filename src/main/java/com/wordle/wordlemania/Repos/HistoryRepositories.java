package com.wordle.wordlemania.Repos;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.wordle.wordlemania.Entity.History;
import com.wordle.wordlemania.Model.HistoryId;

public interface HistoryRepositories extends CrudRepository<History, HistoryId> {
    
    List<History> findAllHistoryByUserIdAndShowableTrueOrderByRoomId (Integer userId);

    List<History> findAllByRoomId (Integer roomId);

    boolean existsByUserIdAndRoomIdAndShowableTrue(Integer userId, Integer roomId);

    History findByUserIdAndRoomId (Integer userId, Integer roomId);
}