package com.wordle.wordlemania.Controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wordle.wordlemania.Entity.User;
import com.wordle.wordlemania.Model.FriendStatus;
import com.wordle.wordlemania.Services.FriendService;
import com.wordle.wordlemania.Services.UserService;
import com.wordle.wordlemania.dto.FriendRequestData;
import com.wordle.wordlemania.dto.ResponseData;
import com.wordle.wordlemania.dto.UserResponseData;

@RestController
@RequestMapping(path = "/Friends")
public class FriendController {

    @Autowired
    private FriendService friendService;

    @Autowired
    private UserService userService;

    @GetMapping("/{idPlayer}")
    public ResponseEntity<ResponseData<List<UserResponseData>>> getAllFriends(@PathVariable int idPlayer) {
        Optional<User> userOptional = userService.getUserById(idPlayer);
        ResponseData<List<UserResponseData>> responseData = new ResponseData<>();

        if (userOptional.isPresent()) {
            responseData.setStatus(true);
            responseData.setPayload(friendService.getAllAccept(idPlayer));
            return ResponseEntity.ok().body(responseData);
        } else {
            responseData.setStatus(false);
            responseData.setPayload(null);
            responseData.getMessages().add("Player Not Found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseData);
        }
    }

    @GetMapping("/Requests/{idPlayer}")
    public ResponseEntity<ResponseData<List<FriendRequestData>>> getAllRequests(@PathVariable int idPlayer) {
        Optional<User> userOptional = userService.getUserById(idPlayer);
        ResponseData<List<FriendRequestData>> responseData = new ResponseData<>();

        if (userOptional.isPresent()) {
            responseData.setStatus(true);
            responseData.setPayload(friendService.getAllPending(idPlayer));
            return ResponseEntity.ok().body(responseData);
        } else {
            responseData.setStatus(false);
            responseData.setPayload(null);
            responseData.getMessages().add("Player Not Found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseData);
        }
    }

    @PostMapping("/{idPlayer}")
    public ResponseEntity<ResponseData<String>> addRequest(@PathVariable int idPlayer,
            @RequestBody FriendRequestData friendData) {
        Optional<User> userOptional = userService.getUserById(idPlayer);
        Optional<User> targetOptional = userService.getUserById(friendData.getId());
        ResponseData<String> responseData = new ResponseData<>();
        responseData.setPayload(null);

        if (userOptional.isPresent() && targetOptional.isPresent()) {
            if (friendService.isExistWithStatus(friendData.getId(), idPlayer, FriendStatus.PENDING)) {
                responseData.getMessages().add("You already sent request to this player");

            } else if (friendService.isExistWithStatus(idPlayer, friendData.getId(), FriendStatus.PENDING)) {
                responseData.getMessages()
                        .add("This player already sent request to you. Please check your friend request.");

            } else if (friendService.isExistWithStatus(idPlayer, friendData.getId(), FriendStatus.ACCEPT)
                    || friendService.isExistWithStatus(friendData.getId(), idPlayer, FriendStatus.ACCEPT)) {
                responseData.getMessages().add("You already friend with this player");

            } else if (idPlayer != friendData.getId()) {
                if (friendService.isExistWithStatus(idPlayer, friendData.getId(), FriendStatus.REJECT)) {
                    friendService.delete(friendData.getId(), idPlayer);
                }

                responseData.getMessages().add(friendService.save(idPlayer, friendData.getId()));
                responseData.setStatus(true);
                return ResponseEntity.ok().body(responseData);

            } else {
                responseData.getMessages().add("Something is wrong with the requested target");
            }
            responseData.setStatus(false);
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(responseData);
        } else {
            responseData.setStatus(false);
            responseData.getMessages().add("Error: Friend Not Found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseData);
        }
    }

    @PutMapping("/{idPlayer}")
    public ResponseEntity<ResponseData<String>> updateRequest(@PathVariable int idPlayer,
            @RequestBody FriendRequestData friendData) {

        Optional<User> playerOptional = userService.getUserById(idPlayer);
        Optional<User> friendOptional = userService.getUserById(friendData.getId());
        ResponseData<String> responseData = new ResponseData<>();
        responseData.setPayload(null);

        if (playerOptional.isPresent() && friendOptional.isPresent()) {
            if (friendData.getStatus() == null) {
                responseData.getMessages().add("Error: New Friend Status is Required");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseData);

            } else if (friendService.isExistWithStatus(friendData.getId(), idPlayer, FriendStatus.ACCEPT)) {
                responseData.setStatus(true);
                responseData.getMessages()
                        .add(friendService.update(friendData.getId(), idPlayer, friendData.getStatus()));
                return ResponseEntity.ok().body(responseData);

            } else if (friendService.isExistWithStatus(idPlayer, friendData.getId(), FriendStatus.PENDING)
                    || friendService.isExistWithStatus(idPlayer, friendData.getId(), FriendStatus.ACCEPT)) {
                responseData.setStatus(true);
                responseData.getMessages()
                        .add(friendService.update(idPlayer, friendData.getId(), friendData.getStatus()));
                return ResponseEntity.ok().body(responseData);

            }
        }

        responseData.setStatus(false);
        responseData.getMessages().add("Error: Friend Request Not Found");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseData);
    }

    @DeleteMapping("/{idPlayer}")
    public ResponseEntity<String> deleteRequest(@PathVariable int idPlayer, @RequestBody FriendRequestData friendData) {
        Optional<User> userOptional = userService.getUserById(idPlayer);
        Optional<User> targetOptional = userService.getUserById(friendData.getId());

        if (userOptional.isPresent() && targetOptional.isPresent()) {
            if (friendService.delete(idPlayer, friendData.getId())) {
                return ResponseEntity.ok("Friend request succesfully deleted");
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: Friend Request Not Found");
    }
}
