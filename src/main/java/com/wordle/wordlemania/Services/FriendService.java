package com.wordle.wordlemania.Services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wordle.wordlemania.Entity.FriendRequest;
import com.wordle.wordlemania.Model.FriendRequestId;
import com.wordle.wordlemania.Model.FriendStatus;
import com.wordle.wordlemania.Repos.FriendReqRepositories;
import com.wordle.wordlemania.Repos.UserRepositories;
import com.wordle.wordlemania.dto.FriendRequestData;
import com.wordle.wordlemania.dto.UserResponseData;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class FriendService {

    @Autowired
    private FriendReqRepositories friendReqRepositories;

    @Autowired
    private UserRepositories userRepositories;

    public boolean isExistAndPending(int idFriend, int idPlayer) {
        return friendReqRepositories.existsByReceiverIdAndSenderIdAndStatus(idFriend, idPlayer, FriendStatus.PENDING);
    }

    public boolean isExistAndAccepted(int idFriend, int idPlayer) {
        return friendReqRepositories.existsByReceiverIdAndSenderIdAndStatus(idFriend, idPlayer, FriendStatus.ACCEPT);
    }

    public boolean isExistWithStatus(int idFriend, int idPlayer, FriendStatus status) {
        return friendReqRepositories.existsByReceiverIdAndSenderIdAndStatus(idFriend, idPlayer, status);
    }

    public List<FriendRequestData> getAllPending(int idPlayer) {
        List<FriendRequest> friendRequests = friendReqRepositories.findAllByReceiverIdAndStatus(idPlayer, FriendStatus.PENDING);
        List<FriendRequestData> listFriendData = new ArrayList<>();
        for (FriendRequest friendRequest : friendRequests) {
            FriendRequestData friendRequestData = new FriendRequestData();
            friendRequestData.setId(friendRequest.getId().getSenderId());
            friendRequestData.setFriendName(friendRequest.getSender().getUserGuest().getName());
            friendRequestData.setStatus(friendRequest.getStatus());
            listFriendData.add(friendRequestData);
        }
        return listFriendData;
    }

    public List<UserResponseData> getAllAccept(int idPlayer) {
        List<FriendRequest> friendList = friendReqRepositories.findAllByReceiverIdAndStatusOrSenderIdAndStatus(idPlayer, FriendStatus.ACCEPT, idPlayer, FriendStatus.ACCEPT);
        List<UserResponseData> listUserData = new ArrayList<>();
        for (FriendRequest friend : friendList) {
            UserResponseData userResponseData = new UserResponseData();
            if(friend.getId().getSenderId() != idPlayer) {
                userResponseData.setUserId(friend.getId().getSenderId());
                userResponseData.setName(friend.getSender().getUserGuest().getName());
                userResponseData.setScore(userRepositories.findById(friend.getId().getSenderId()).get().getScore());
            } else {
                userResponseData.setUserId(friend.getId().getReceiverId());
                userResponseData.setName(friend.getReceiver().getUserGuest().getName());
                userResponseData.setScore(userRepositories.findById(friend.getId().getReceiverId()).get().getScore());
            }
            listUserData.add(userResponseData);
        }
        return listUserData;
    }

    public String save(int idPlayer, int idFriend) {
        FriendRequest friendRequest = new FriendRequest();
        FriendRequestId friendRequestId = new FriendRequestId();
        friendRequestId.setSenderId(idPlayer);
        friendRequestId.setReceiverId(idFriend);
        friendRequest.setId(friendRequestId);
        friendRequest.setStatus(FriendStatus.PENDING);
        friendReqRepositories.save(friendRequest);
        return "Successfully send friend request";
    }

    public String update(int idPlayer, int idFriend, FriendStatus status) {
        FriendRequest friendRequest = new FriendRequest();
        FriendRequestId friendRequestId = new FriendRequestId();
        friendRequestId.setSenderId(idFriend);
        friendRequestId.setReceiverId(idPlayer);
        friendRequest.setId(friendRequestId);
        friendRequest.setStatus(status);
        friendReqRepositories.save(friendRequest);

        if(status == FriendStatus.ACCEPT) {
            return "Friend Request Accepted";
        } else {
            return "Friend Request Rejected";
        }
    }

    public boolean delete(int idPlayer, int idTarget) {
        FriendRequestId friendRequestId = new FriendRequestId();
        friendRequestId.setSenderId(idPlayer);
        friendRequestId.setReceiverId(idTarget);

        if (friendReqRepositories.existsById(friendRequestId)) {
            friendReqRepositories.deleteById(friendRequestId);
            return true;
        } else {
            return false;
        }

    }
}
