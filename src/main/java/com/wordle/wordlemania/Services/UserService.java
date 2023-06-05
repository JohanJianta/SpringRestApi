package com.wordle.wordlemania.Services;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wordle.wordlemania.Entity.Guest;
import com.wordle.wordlemania.Entity.User;
import com.wordle.wordlemania.Model.PlayerStatus;
import com.wordle.wordlemania.Repos.UserRepositories;
import com.wordle.wordlemania.Utils.PasswordUtils;
import com.wordle.wordlemania.dto.UserResponseData;
import com.wordle.wordlemania.dto.UserRequest;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepositories userRepository;

    public boolean isExistByGuestId(int guestId) {
        return userRepository.existsByGuestId(guestId);
    }

    public UserResponseData getUserData(int id) {
        User user = userRepository.findById(id).get();
        UserResponseData userPublic = new UserResponseData();
        userPublic.setGuestId(user.getUserGuest().getId());
        userPublic.setName(user.getUserGuest().getName());
        userPublic.setUserId(user.getId());
        // userPublic.setEmail(user.getEmail());
        userPublic.setScore(user.getScore());
        userPublic.setTotalPlay(user.getTotalPlay());
        userPublic.setTotalWin(user.getTotalWin());
        userPublic.setStatus(user.getStatus());
        return userPublic;
    }

    public Optional<User> getUserById(int id) {
        return userRepository.findById(id);
    }

    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public int loginUser(String email, String pass) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (PasswordUtils.generateHashPass(pass, user.getSalt()).equals(user.getPassword())) {
                return user.getId();
            }
        }

        return 0;
    }

    public String registerUser(Guest guest, UserRequest userRequest) {
        guest.setName(userRequest.getName());
        User user = new User();
        user.setUserGuest(guest);
        user.setEmail(userRequest.getEmail());
        String salt;
        try {
            salt = PasswordUtils.generateSalt();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "Something went wrong during creating account, please try again";
        }
        user.setPassword(PasswordUtils.generateHashPass(userRequest.getPassword(), salt));
        user.setSalt(salt);
        user.setStatus(PlayerStatus.Online);
        userRepository.save(user);
        return "Player successfully registered";
    }

    public UserResponseData update(UserResponseData userResponseData, User user) {
        UserResponseData userDataChanges = new UserResponseData();

        if (userResponseData.getName() != null && !userResponseData.getName().isEmpty()) {
            user.getUserGuest().setName(userResponseData.getName());
            userDataChanges.setName(userResponseData.getName());
        }

        if (userResponseData.getEmail() != null && !userResponseData.getEmail().isEmpty()) {
            user.setEmail(userResponseData.getEmail());
            userDataChanges.setEmail(userResponseData.getEmail());
        }

        if (userResponseData.getPassword() != null && !userResponseData.getPassword().isEmpty()) {
            String salt;
            try {
                salt = PasswordUtils.generateSalt();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
                return null;
            }
            user.setPassword(PasswordUtils.generateHashPass(userResponseData.getPassword(), salt));
            user.setSalt(salt);
        }

        if (userResponseData.getScore() != null) {
            user.setScore(userResponseData.getScore());
            userDataChanges.setScore(userResponseData.getScore());
        }

        if (userResponseData.getTotalPlay() != null) {
            user.setTotalPlay(userResponseData.getTotalPlay());
            userDataChanges.setTotalPlay(userResponseData.getTotalPlay());
        }

        if (userResponseData.getTotalWin() != null) {
            user.setTotalWin(userResponseData.getTotalWin());
            userDataChanges.setTotalWin(userResponseData.getTotalWin());
        }

        if (userResponseData.getStatus() != null) {
            user.setStatus(userResponseData.getStatus());
            userDataChanges.setStatus(userResponseData.getStatus());
        }

        userRepository.save(user);

        return userDataChanges;
    }

    public void delete(int id) {
        userRepository.deleteById(id);
    }

    public List<UserResponseData> getTopPlayers() {
        List<User> topPlayers = userRepository.findTop3ByOrderByScoreDescTotalWinDescTotalPlayAsc();
        List<UserResponseData> topPlayersData = new ArrayList<>();
        for (User user : topPlayers) {
            UserResponseData userPublic = new UserResponseData();
            // userPublic.setId(user.getId());
            userPublic.setName(user.getUserGuest().getName());
            userPublic.setScore(user.getScore());
            // userPublic.setTotalPlay(user.getTotalPlay());
            userPublic.setTotalWin(user.getTotalWin());
            topPlayersData.add(userPublic);
        }

        return topPlayersData;
    }
}
