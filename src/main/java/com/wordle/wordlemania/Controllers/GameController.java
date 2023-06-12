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

import com.wordle.wordlemania.Entity.Game;
import com.wordle.wordlemania.Entity.Guest;
import com.wordle.wordlemania.Entity.Room;
import com.wordle.wordlemania.Model.RoomStatus;
import com.wordle.wordlemania.Services.GamePlayerService;
import com.wordle.wordlemania.Services.GameService;
import com.wordle.wordlemania.Services.GuestService;
import com.wordle.wordlemania.Services.RoomService;
import com.wordle.wordlemania.dto.GamePlayerRequest;
import com.wordle.wordlemania.dto.HistoryData;
import com.wordle.wordlemania.dto.ResponseData;
import com.wordle.wordlemania.dto.RoomResponseData;

import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/Game")
public class GameController {

    @Autowired
    private RoomService roomService;

    @Autowired
    private GameService gameService;

    @Autowired
    private GamePlayerService gamePlayerService;

    @Autowired
    private GuestService guestService;

    @GetMapping
    public ResponseEntity<ResponseData<Integer>> findRandomGame() {
        Game gameOptional = gameService.getRandomGame();
        ResponseData<Integer> responseData = new ResponseData<>();

        if (gameOptional != null) {
            responseData.setStatus(true);
            responseData.setPayload(gameOptional.getId());
            return ResponseEntity.ok().body(responseData);
        } else {
            responseData.setStatus(false);
            responseData.setPayload(null);
            responseData.getMessages().add("There are no public rooms available");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseData);
        }
    }

    @GetMapping("/{gameCode}")
    public ResponseEntity<ResponseData<Boolean>> findGameByCode(@PathVariable(value = "gameCode") int id) {
        Game gameOptional = gameService.getPrivateRoom(id);
        ResponseData<Boolean> responseData = new ResponseData<>();

        if (gameOptional != null) {
            responseData.setStatus(true);
            responseData.setPayload(true);
            return ResponseEntity.ok().body(responseData);
        } else {
            responseData.setStatus(false);
            responseData.setPayload(false);
            responseData.getMessages().add("Invalid Room Code");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseData);
        }

    }

    @GetMapping("/Data/{roomCode}")
    public ResponseEntity<ResponseData<HistoryData>> findRoomData(@PathVariable(value = "roomCode") int id) {
        Optional<Game> gameOptional = gameService.getGameById(id);
        ResponseData<HistoryData> responseData = new ResponseData<>();

        if (gameOptional.isPresent()) {
            responseData.setStatus(true);
            responseData.setPayload(roomService.getRoomData(gameOptional.get()));
            return ResponseEntity.ok().body(responseData);
        } else {
            responseData.setStatus(false);
            responseData.setPayload(null);
            responseData.getMessages().add("Room data not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseData);
        }

    }

    @PostMapping
    public ResponseEntity<ResponseData<Integer>> createGame(@RequestBody String status) {
        ResponseData<Integer> responseData = new ResponseData<>();

        if (status.equalsIgnoreCase("Public") || status.equalsIgnoreCase("Private")) {
            Game game;
            if (status.equalsIgnoreCase("Public")) {
                game = gameService.createGame(RoomStatus.Public);
            } else {
                game = gameService.createGame(RoomStatus.Private);
            }
            roomService.save(game);
            responseData.setPayload(game.getId());
            responseData.setStatus(true);
            responseData.getMessages().add("Room successfully created");
            return ResponseEntity.ok(responseData);
        } else {
            responseData.setStatus(false);
            responseData.setPayload(null);
            responseData.getMessages().add("Room status is not valid");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseData);
        }
    }

    @PostMapping("/Data")
    public ResponseEntity<ResponseData<RoomResponseData>> createNewRoom(@RequestBody int gameId) {
        Optional<Game> gameOptional = gameService.getGameById(gameId);
        ResponseData<RoomResponseData> responseData = new ResponseData<>();

        if (gameOptional.isPresent()) {
            Game game = gameOptional.get();
            game.setStatus(RoomStatus.Private);

            responseData.setPayload(roomService.save(game));
            responseData.setStatus(true);
            responseData.getMessages().add("Room successfully created");
            return ResponseEntity.ok(responseData);
        } else {
            responseData.setStatus(false);
            responseData.setPayload(null);
            responseData.getMessages().add("Gameroom is not found");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseData);
        }
    }

    @PostMapping("/Player")
    public ResponseEntity<ResponseData<Boolean>> joinRoom(@Valid @RequestBody GamePlayerRequest playerRequest, Errors errors) {
        Optional<Game> optionalGame = gameService.getGameById(playerRequest.getGameId());
        Optional<Guest> optionalGuest = guestService.getGuestById(playerRequest.getPlayerId());
        ResponseData<Boolean> responseData = new ResponseData<>();

        if (errors.hasErrors()) {
            for (ObjectError error : errors.getAllErrors()) {
                responseData.getMessages().add(error.getDefaultMessage());
            }
            responseData.setStatus(false);
            responseData.setPayload(null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseData);
        } else if (optionalGame.isPresent() && optionalGuest.isPresent()) {
            responseData.setStatus(true);
            responseData.setPayload(gamePlayerService.savePlayer(playerRequest.getPlayerId(), playerRequest.getGameId()));
            responseData.getMessages().add("Successfully add player");
            return ResponseEntity.ok().body(responseData);
        } else {
            responseData.setStatus(false);
            responseData.setPayload(null);
            responseData.getMessages().add("Player id or game id is not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseData);
        }
    }

    @PutMapping("/Player")
    public ResponseEntity<ResponseData<Boolean>> leaveRoom(@Valid @RequestBody GamePlayerRequest playerRequest, Errors errors) {
        ResponseData<Boolean> responseData = new ResponseData<>();

        if (errors.hasErrors()) {
            for (ObjectError error : errors.getAllErrors()) {
                responseData.getMessages().add(error.getDefaultMessage());
            }
            responseData.setStatus(false);
            responseData.setPayload(null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseData);
        } else if (gamePlayerService.isExist(playerRequest.getPlayerId(), playerRequest.getGameId())) {
            responseData.setStatus(true);
            responseData.setPayload(gamePlayerService.removePlayer(playerRequest.getPlayerId(), playerRequest.getGameId()));
            responseData.getMessages().add("Successfully remove player");
            return ResponseEntity.ok().body(responseData);
        } else {
            responseData.setStatus(false);
            responseData.setPayload(null);
            responseData.getMessages().add("Player id or game id is not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseData);
        }
    }

    @PutMapping("/Data/{roomCode}")
    public ResponseEntity<ResponseData<RoomResponseData>> updateRoomData(@PathVariable(value = "roomCode") int id,
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

    @DeleteMapping("/{gameCode}")
    public ResponseEntity<String> disableGameroom(@PathVariable(value = "gameCode") int id) {
        Optional<Game> optionalGame = gameService.getGameById(id);

        if (optionalGame.isPresent()) {
            gameService.finishGame(optionalGame.get());
            return ResponseEntity.ok("Gameroom succesfully deleted");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: Gameroom Not Found");
        }
    }

    @DeleteMapping("/Data/{roomCode}")
    public ResponseEntity<String> deleteRoom(@PathVariable(value = "roomCode") int id) {
        Optional<Room> userOptional = roomService.getRoomById(id);

        if (userOptional.isPresent()) {
            roomService.delete(id);
            return ResponseEntity.ok("Room succesfully deleted");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: Room Not Found");
        }
    }
}