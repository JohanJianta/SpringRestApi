package com.wordle.wordlemania.Controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wordle.wordlemania.Entity.Room;
import com.wordle.wordlemania.Entity.User;
import com.wordle.wordlemania.Services.HistoryService;
import com.wordle.wordlemania.Services.RoomService;
import com.wordle.wordlemania.Services.UserService;
import com.wordle.wordlemania.dto.HistoryData;
import com.wordle.wordlemania.dto.HistoryRequest;
import com.wordle.wordlemania.dto.ResponseData;

import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/History")
public class HistoryController {

    @Autowired
    private HistoryService historyService;

    @Autowired
    private UserService userService;

    @Autowired
    private RoomService roomService;

    @GetMapping("/{idPlayer}")
    public ResponseEntity<ResponseData<List<HistoryData>>> findHistories(@PathVariable int idPlayer) {
        Optional<User> userOptional = userService.getUserById(idPlayer);
        ResponseData<List<HistoryData>> listRoom = new ResponseData<>();

        if (userOptional.isPresent()) {
            listRoom.setStatus(true);
            listRoom.setPayload(historyService.getAllHistory(idPlayer));
            return ResponseEntity.ok().body(listRoom);
        } else {
            listRoom.setStatus(false);
            listRoom.setPayload(null);
            listRoom.getMessages().add("Error: Histories Not Found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(listRoom);
        }

    }

    @PostMapping()
    public ResponseEntity<ResponseData<String>> addToHistory(@Valid @RequestBody HistoryRequest historyRequest,
            Errors errors) {
        Optional<User> userOptional = userService.getUserById(historyRequest.getUserId());
        Optional<Room> roomOptional = roomService.getRoomById(historyRequest.getRoomId());
        ResponseData<String> responseData = new ResponseData<>();
        responseData.setPayload(null);
        responseData.setStatus(false);

        if (errors.hasErrors()) {
            for (ObjectError error : errors.getAllErrors()) {
                responseData.getMessages().add(error.getDefaultMessage());
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseData);

        } else if (userOptional.isPresent() && roomOptional.isPresent()) {
            responseData.setStatus(true);
            responseData.getMessages()
                    .add(historyService.saveUser(historyRequest.getUserId(), historyRequest.getRoomId()));
            return ResponseEntity.ok().body(responseData);

        } else {
            responseData.getMessages().add("Error: User Id or Room Id Not Found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseData);
        }

    }

    @DeleteMapping("/{idPlayer}-{idRoom}")
    public ResponseEntity<ResponseData<String>> deleteHistory(@PathVariable int idPlayer, @PathVariable int idRoom) {
        Optional<User> userOptional = userService.getUserById(idPlayer);
        Optional<Room> roomOptional = roomService.getRoomById(idRoom);
        ResponseData<String> responseData = new ResponseData<>();
        responseData.setPayload(null);

        if (userOptional.isPresent() && roomOptional.isPresent()) {
            if (historyService.isExist(idPlayer, idRoom)) {
                historyService.delete(idPlayer, idRoom);
                responseData.setStatus(true);
                responseData.getMessages().add("History succesfully deleted");
                return ResponseEntity.ok().body(responseData);
            }
        }
        responseData.setStatus(false);
        responseData.getMessages().add("Error: History Not Found");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseData);
    }
}
