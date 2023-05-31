package com.wordle.wordlemania.Services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wordle.wordlemania.Entity.History;
import com.wordle.wordlemania.Entity.Room;
import com.wordle.wordlemania.Model.HistoryId;
import com.wordle.wordlemania.Repos.HistoryRepositories;
import com.wordle.wordlemania.Repos.UserRepositories;
import com.wordle.wordlemania.dto.HistoryData;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class HistoryService {

    @Autowired
    private HistoryRepositories historyRepository;

    @Autowired
    private UserRepositories userRepository;

    public boolean isExist(int userId, int roomId) {
        return historyRepository.existsByUserIdAndRoomIdAndShowableTrue(userId, roomId);
    }

    public List<HistoryData> getAllHistory(int idPlayer) {
        List<History> histories = historyRepository.findAllHistoryByUserIdAndShowableTrueOrderByRoomId(idPlayer);
        List<HistoryData> historiesData = new ArrayList<>();
        if (!histories.isEmpty()) {
            for (History history : histories) {
                HistoryData data = new HistoryData();
                Room room = history.getRoom();
                data.setId(room.getId());
                data.setWin(room.isWin());
                data.setWord(room.getWord());
                data.setDate(room.getDate());
                data.setScore(room.getScorePrize());

                List<History> gamePlayers = historyRepository.findAllByRoomId(room.getId());
                for (History player : gamePlayers) {
                    data.addPlayer(userRepository.findById(player.getId().getUserId()).get().getName());
                }

                historiesData.add(data);
            }
        }
        return historiesData;
    }

    public String saveUser(int userId, int roomId) {
        History history = new History();
        HistoryId historyId = new HistoryId();
        historyId.setUserId(userId);
        historyId.setRoomId(roomId);
        history.setId(historyId);
        history.setShowable(true);
        historyRepository.save(history);

        return "Player succesfully added to history";
    }

    public void delete(int userId, int roomId) {
        History history = historyRepository.findByUserIdAndRoomId(userId, roomId);
        history.setShowable(false);
        historyRepository.save(history);
    }

}
