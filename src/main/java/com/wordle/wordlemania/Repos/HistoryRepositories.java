package com.wordle.wordlemania.Repos;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.wordle.wordlemania.Entity.History;
import com.wordle.wordlemania.Entity.Room;
import com.wordle.wordlemania.Model.HistoryId;

public interface HistoryRepositories extends CrudRepository<History, HistoryId> {

    // List<History> findAllHistoryByGuestIdAndShowableTrueOrderByRoomId (Integer
    // guestId);

    @Query("SELECT h FROM History h JOIN h.room r WHERE h.guest.id = :guestId AND h.showable = true GROUP BY r.id HAVING COUNT(DISTINCT r.id) <= 20 ORDER BY r.date DESC")
    List<History> findTop20DistinctRoomIdsByGuestIdAndShowableTrueOrderByDateDesc(@Param("guestId") Integer guestId);

    List<History> findAllByRoom(Room room);

    boolean existsByGuestIdAndRoomIdAndShowableTrue(Integer guestId, Integer roomId);

    History findByGuestIdAndRoomId(Integer guestId, Integer roomId);
}