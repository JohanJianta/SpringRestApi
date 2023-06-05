package com.wordle.wordlemania.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wordle.wordlemania.Entity.Game;
import com.wordle.wordlemania.Entity.Room;
import com.wordle.wordlemania.Repos.RoomRepositories;
import com.wordle.wordlemania.Repos.WordRepositories;
import com.wordle.wordlemania.dto.HistoryData;
import com.wordle.wordlemania.dto.RoomResponseData;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class RoomService {

    @Autowired
    private RoomRepositories roomRepository;

    @Autowired
    private WordRepositories wordRepository;

    @Autowired
    private GamePlayerService gamePlayerService;
    
    @Autowired
    private GuestService guestService;

    // public Iterable<Room> getAllRooms() {
    //     return roomRepository.findAll();
    // }

    public Optional<Room> getRoomById(int id) {
        return roomRepository.findById(id);
    }

    public HistoryData getRoomData(Game game) {
        Room savedRoom = roomRepository.findByGameroomOrderByDateDesc(game);
        HistoryData historyData = new HistoryData();
        historyData.setGameCode(game.getId());
        historyData.setRoomId(savedRoom.getId());
        historyData.setWord(savedRoom.getWord().getWord());
        historyData.setGuessesTry(savedRoom.getGuessesTry());

        List<Integer> playerIds = gamePlayerService.getAllPlayerIds(game.getId());
        for (Integer playerId : playerIds) {
            historyData.addPlayerName(guestService.getGuestById(playerId).get().getName());
            historyData.addPlayerId(playerId);
        }

        return historyData;
    }

    public RoomResponseData save(Game game) {
        Room room = new Room(5, 6, 100, false);
        room.setWord(wordRepository.findRandomByLength(5));
        room.setGameroom(game);
        Room savedRoom = roomRepository.save(room);

        RoomResponseData roomData = new RoomResponseData();
        roomData.setGameCode(game.getId());
        roomData.setRoomId(savedRoom.getId());
        roomData.setWord(savedRoom.getWord().getWord());
        roomData.setGuessesTry(savedRoom.getGuessesTry());
        // roomData.setLength(savedRoom.getLength());
        return roomData;
    }

    public RoomResponseData update(RoomResponseData roomResponseData, Room room) {
        RoomResponseData roomDataChanges = new RoomResponseData();

        if (roomResponseData.getLength() != null) {
            room.setWord(wordRepository.findRandomByLength(roomResponseData.getLength()));
            roomDataChanges.setWord(room.getWord().getWord());
        }

        if (roomResponseData.getGuessesTry() != null) {
            room.setGuessesTry(roomResponseData.getGuessesTry());
            roomDataChanges.setGuessesTry(roomResponseData.getGuessesTry());
        }

        if (roomResponseData.getScorePrize() != null) {
            room.setScorePrize(roomResponseData.getScorePrize());
            roomDataChanges.setScorePrize(roomResponseData.getScorePrize());
        }

        if (roomResponseData.getWin() != null) {
            room.setWin(roomResponseData.getWin());
            roomDataChanges.setWin(roomResponseData.getWin());
        }

        roomRepository.save(room);

        return roomDataChanges;
    }

    public void delete(int id) {
        roomRepository.deleteById(id);
    }
}