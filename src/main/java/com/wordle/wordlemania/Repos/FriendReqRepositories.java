package com.wordle.wordlemania.Repos;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.wordle.wordlemania.Entity.FriendRequest;
import com.wordle.wordlemania.Model.FriendRequestId;
import com.wordle.wordlemania.Model.FriendStatus;

public interface FriendReqRepositories extends CrudRepository<FriendRequest, FriendRequestId>{

    List<FriendRequest> findAllByReceiverIdAndStatus(Integer receiverId, FriendStatus status);

    List<FriendRequest> findAllByReceiverIdAndStatusOrSenderIdAndStatus(Integer receiverId, FriendStatus status1, Integer senderId, FriendStatus status2);

    boolean existsByReceiverIdAndSenderIdAndStatus(Integer receiverId, Integer senderId, FriendStatus status);
}