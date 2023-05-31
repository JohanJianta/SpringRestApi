package com.wordle.wordlemania.Services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import com.wordle.wordlemania.Entity.Room;
import com.wordle.wordlemania.Repos.RoomRepositories;
import com.wordle.wordlemania.dto.RoomRequest;
import com.wordle.wordlemania.dto.RoomResponseData;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class RoomService {

    @Autowired
    private RoomRepositories roomRepository;

    @GetMapping
    public Iterable<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    public Optional<Room> getRoomById(int id) {
        return roomRepository.findById(id);
    }

    public Room save(RoomRequest roomRequest, int id) {
        Room room = new Room(roomRequest.getWord(), roomRequest.getGuessesTry(), roomRequest.getScorePrize(), false);
        room.setId(id);
        return roomRepository.save(room);
    }

    public RoomResponseData update(RoomResponseData roomResponseData, Room room) {
        RoomResponseData roomDataChanges = new RoomResponseData();

        if (roomResponseData.getWord() != null && !roomResponseData.getWord().isEmpty()) {
            room.setWord(roomResponseData.getWord());
            roomDataChanges.setWord(roomResponseData.getWord());
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