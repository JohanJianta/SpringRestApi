package com.wordle.wordlemania.Repos;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.wordle.wordlemania.Entity.History;
import com.wordle.wordlemania.Entity.Room;
import com.wordle.wordlemania.Model.HistoryId;

public interface HistoryRepositories extends CrudRepository<History, HistoryId> {
    
    List<History> findAllHistoryByGuestIdAndShowableTrueOrderByRoomId (Integer guestId);

    List<History> findAllByRoom (Room room);

    boolean existsByGuestIdAndRoomIdAndShowableTrue (Integer guestId, Integer roomId);

    History findByGuestIdAndRoomId (Integer guestId, Integer roomId);
}