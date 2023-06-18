package com.wordle.wordlemania.Controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
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

import com.wordle.wordlemania.Entity.Guest;
import com.wordle.wordlemania.Entity.User;
import com.wordle.wordlemania.Services.GuestService;
import com.wordle.wordlemania.Services.UserService;
import com.wordle.wordlemania.dto.ResponseData;
import com.wordle.wordlemania.dto.UserResponseData;
import com.wordle.wordlemania.dto.UserRequest;

import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/Player")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private GuestService guestService;

    @PostMapping("/Guest")
    public Guest createGuest(@RequestBody String name) {

        if (name != null && !name.isEmpty()) {
            return guestService.saveGuest(name);
        } else {
            return guestService.saveGuest("Guest");
        }

    }

    @PostMapping("/Login")
    public ResponseEntity<ResponseData<UserResponseData>> authenticateUser(@Valid @RequestBody UserRequest userRequest,
            Errors errors) {
        ResponseData<UserResponseData> responseData = new ResponseData<>();
        int playerId;

        if (errors.hasErrors()) {
            for (ObjectError error : errors.getAllErrors()) {
                responseData.getMessages().add(error.getDefaultMessage());
            }
            responseData.setStatus(false);
            responseData.setPayload(null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseData);
        } else if ((playerId = userService.loginUser(userRequest.getEmail(), userRequest.getPassword())) != 0) {
            responseData.setStatus(true);
            responseData.setPayload(userService.getUserData(playerId));
            responseData.getMessages().add("Authentication Success!");
            return ResponseEntity.ok().body(responseData);
        } else {
            responseData.setStatus(false);
            responseData.setPayload(null);
            responseData.getMessages().add("Incorrect Email or Password");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseData);
        }

    }

    // buat satu player
    @PostMapping("/Register")
    public ResponseEntity<ResponseData<UserResponseData>> createUser(@Valid @RequestBody UserRequest userRequest,
            Errors errors) {
        Optional<Guest> guestOptional = guestService.getGuestById(userRequest.getGuestId());
        ResponseData<UserResponseData> responseData = new ResponseData<>();

        if (errors.hasErrors()) {
            for (ObjectError error : errors.getAllErrors()) {
                responseData.getMessages().add(error.getDefaultMessage());
            }
            responseData.setStatus(false);
            responseData.setPayload(null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseData);
        } else if (userService.getUserByEmail(userRequest.getEmail()).isPresent()) {
            responseData.setStatus(false);
            responseData.setPayload(null);
            responseData.getMessages()
                    .add(String.format("The email you used already linked to another account", userRequest.getEmail()));
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(responseData);
        } else {
            int playerId;
            if (!guestOptional.isPresent() || userService.isExistByGuestId(userRequest.getGuestId())) {
                playerId = userService.registerUser(guestService.saveGuest("Guest"), userRequest);
            } else {
                playerId = userService.registerUser(guestOptional.get(), userRequest);
            }

            if (playerId == 0) {
                responseData.setStatus(false);
                responseData.setPayload(null);
                responseData.getMessages().add("Something went wrong during registration. Please try again");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseData);
            } else {
                responseData.setStatus(true);
                responseData.setPayload(userService.getUserData(playerId));
                return ResponseEntity.ok(responseData);
            }
        }
    }

    @GetMapping
    public ResponseEntity<ResponseData<List<UserResponseData>>> findRandomPlayer() {
        ResponseData<List<UserResponseData>> responseData = new ResponseData<>();
        responseData.setStatus(true);
        responseData.setPayload(userService.get5Random());
        return ResponseEntity.ok().body(responseData);
    }

    @GetMapping("/{idPlayer}")
    public ResponseEntity<ResponseData<UserResponseData>> findUser(@PathVariable(value = "idPlayer") int id) {
        Optional<User> userOptional = userService.getUserById(id);
        ResponseData<UserResponseData> responseData = new ResponseData<>();

        if (userOptional.isPresent()) {
            responseData.setStatus(true);
            responseData.setPayload(userService.getUserData(id));
            return ResponseEntity.ok().body(responseData);
        } else {
            responseData.setStatus(false);
            responseData.setPayload(null);
            responseData.getMessages().add("Player Not Found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseData);
        }
    }

    @GetMapping("/Leaderboard")
    public ResponseEntity<ResponseData<List<UserResponseData>>> findTopPlayer() {
        ResponseData<List<UserResponseData>> responseData = new ResponseData<>();
        responseData.setStatus(true);
        responseData.setPayload(userService.getTopPlayers());
        return ResponseEntity.ok().body(responseData);
    }

    // update info satu player
    @PutMapping("/{idPlayer}")
    public ResponseEntity<ResponseData<UserResponseData>> updateUser(@PathVariable(value = "idPlayer") int id,
            @Valid @RequestBody UserResponseData userResponseData, Errors errors) {
        ResponseData<UserResponseData> responseData = new ResponseData<>();
        Optional<User> userOptional = userService.getUserById(id);

        if (errors.hasErrors()) {
            for (ObjectError error : errors.getAllErrors()) {
                responseData.getMessages().add(error.getDefaultMessage());
            }
            responseData.setStatus(false);
            responseData.setPayload(null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseData);
        } else if (userOptional.isPresent()) {
            responseData.setStatus(true);
            responseData.setPayload(userService.update(userResponseData, userOptional.get()));
            responseData.getMessages().add("Player's data updated");
            return ResponseEntity.ok(responseData);
        } else {
            responseData.setStatus(false);
            responseData.setPayload(null);
            responseData.getMessages().add("Player Not Found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseData);
        }
    }

    // hapus satu player
    @DeleteMapping("/{idPlayer}")
    public ResponseEntity<String> deleteUser(@PathVariable(value = "idPlayer") int id) {
        Optional<User> userOptional = userService.getUserById(id);

        if (userOptional.isPresent()) {
            userService.delete(id);

            return ResponseEntity.ok("Player succesfully deleted");
        } else {
            return ResponseEntity.status(HttpStatusCode.valueOf(404)).body("Error: Player Not Found");
        }
    }
}