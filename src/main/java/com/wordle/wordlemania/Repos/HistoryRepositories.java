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

    @Query(value = "SELECT h.* FROM history h " +
            "JOIN room_data r ON h.room_id = r.room_id " +
            "WHERE h.guest_id = :guestId " +
            "GROUP BY r.room_id " +
            "ORDER BY r.room_date DESC " +
            "LIMIT 20", nativeQuery = true)
    List<History> findTop20HistoriesByGuestIdOrderByDateDesc(@Param("guestId") Integer guestId);

    List<History> findAllByRoom(Room room);

    boolean existsByGuestIdAndRoomIdAndShowableTrue(Integer guestId, Integer roomId);

    History findByGuestIdAndRoomId(Integer guestId, Integer roomId);
}