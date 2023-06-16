package com.wordle.wordlemania.Services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wordle.wordlemania.Entity.History;
import com.wordle.wordlemania.Entity.Room;
import com.wordle.wordlemania.Model.HistoryId;
import com.wordle.wordlemania.Repos.HistoryRepositories;
import com.wordle.wordlemania.dto.HistoryData;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class HistoryService {

    @Autowired
    private HistoryRepositories historyRepository;

    public boolean isExist(int guestId, int roomId) {
        return historyRepository.existsByGuestIdAndRoomIdAndShowableTrue(guestId, roomId);
    }

    public List<HistoryData> getAllHistory(int idPlayer) {
        List<History> histories = historyRepository.findTop20DistinctRoomIdsByGuestIdAndShowableTrueOrderByDateDesc(idPlayer);
        List<HistoryData> historiesData = new ArrayList<>();
        if (!histories.isEmpty()) {
            for (History history : histories) {
                HistoryData data = new HistoryData();
                Room room = history.getRoom();
                data.setRoomId(room.getId());
                data.setWin(room.isWin());
                data.setWord(room.getWord().getWord());
                data.setDate(room.getDate());
                data.setScore(history.getScoreGain());

                List<History> gamePlayers = historyRepository.findAllByRoom(room);
                for (History player : gamePlayers) {
                    data.addPlayerName(player.getGuest().getName());
                }

                historiesData.add(data);
            }
        }
        return historiesData;
    }

    public String savePlayer(int roomId, int guestId, int score) {
        History history = new History();
        history.setId(new HistoryId(roomId, guestId));
        history.setScoreGain(score);
        history.setShowable(true);
        historyRepository.save(history);
        return "Player succesfully added to history";
    }

    public void delete(int guestId, int roomId) {
        History history = historyRepository.findByGuestIdAndRoomId(guestId, roomId);
        history.setShowable(false);
        historyRepository.save(history);
    }

}
