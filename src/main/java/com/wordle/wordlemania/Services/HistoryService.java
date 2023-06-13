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

    @Autowired
    private GamePlayerService gamePlayerService;

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
                data.setScore(room.getScorePrize());

                List<History> gamePlayers = historyRepository.findAllByRoom(room);
                for (History player : gamePlayers) {
                    data.addPlayerName(player.getGuest().getName());
                }

                historiesData.add(data);
            }
        }
        return historiesData;
    }

    public String saveAllPlayers(int gameId, int roomId) {
        List<Integer> playerIds = gamePlayerService.getAllPlayerIds(gameId);
        for (Integer playerId : playerIds) {
            History history = new History();
            history.setId(new HistoryId(roomId, playerId));
            history.setShowable(true);
            historyRepository.save(history);
        }
        return "All players succesfully added to history";
    }

    public void delete(int guestId, int roomId) {
        History history = historyRepository.findByGuestIdAndRoomId(guestId, roomId);
        history.setShowable(false);
        historyRepository.save(history);
    }

}
