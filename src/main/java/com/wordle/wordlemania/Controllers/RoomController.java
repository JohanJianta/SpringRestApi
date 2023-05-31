package com.wordle.wordlemania.Controllers;

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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wordle.wordlemania.Entity.Room;
import com.wordle.wordlemania.Services.RoomService;
import com.wordle.wordlemania.dto.ResponseData;
import com.wordle.wordlemania.dto.RoomRequest;
import com.wordle.wordlemania.dto.RoomResponseData;

import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/Room")
public class RoomController {

    @Autowired
    private RoomService roomService;

    // @GetMapping
    // public Iterable<Room> findAllRooms() {
    //     return roomService.getAllRooms();
    // }

    @GetMapping("/{roomCode}")
    public ResponseEntity<ResponseData<Room>> findRoom(@PathVariable(value = "roomCode") int id) {
        Optional<Room> userOptional = roomService.getRoomById(id);
        ResponseData<Room> responseData = new ResponseData<>();

        if (userOptional.isPresent()) {
            responseData.setStatus(true);
            responseData.setPayload(userOptional.get());
            return ResponseEntity.ok().body(responseData);
        } else {
            responseData.setStatus(false);
            responseData.setPayload(null);
            responseData.getMessages().add("Invalid Room Code");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseData);
        }

    }

    @PostMapping
    public ResponseEntity<ResponseData<Room>> createRoom(@Valid @RequestBody RoomRequest room, Errors errors) {

        ResponseData<Room> responseData = new ResponseData<>();

        if (errors.hasErrors()) {
            for (ObjectError error : errors.getAllErrors()) {
                responseData.getMessages().add(error.getDefaultMessage());
            }
            responseData.setStatus(false);
            responseData.setPayload(null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseData);
        } else {
            responseData.setStatus(true);
            responseData.setPayload(roomService.save(room, 0));
            responseData.getMessages().add("Room successfully created");
            return ResponseEntity.ok(responseData);
        }
    }

    @PutMapping("/{roomCode}")
    public ResponseEntity<ResponseData<RoomResponseData>> updateUser(@PathVariable(value = "roomCode") int id,
            @Valid @RequestBody RoomResponseData roomResponseData, Errors errors) {
        Optional<Room> optionalRoom = roomService.getRoomById(id);

        if (optionalRoom.isPresent()) {
            ResponseData<RoomResponseData> responseData = new ResponseData<>();

            if (errors.hasErrors()) {
                for (ObjectError error : errors.getAllErrors()) {
                    responseData.getMessages().add(error.getDefaultMessage());
                }
                responseData.setStatus(false);
                responseData.setPayload(null);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseData);
            } else {
                responseData.setStatus(true);
                responseData.setPayload(roomService.update(roomResponseData, optionalRoom.get()));
                responseData.getMessages().add("Room's data updated");
                return ResponseEntity.ok(responseData);
            }

        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{roomCode}")
    public ResponseEntity<String> deleteUser(@PathVariable(value = "roomCode") int id) {
        Optional<Room> userOptional = roomService.getRoomById(id);

        if (userOptional.isPresent()) {
            roomService.delete(id);
            return ResponseEntity.ok("Room succesfully deleted");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: Room Not Found");
        }
    }
}
