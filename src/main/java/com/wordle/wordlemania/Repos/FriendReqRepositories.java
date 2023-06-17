package com.wordle.wordlemania.Repos;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.wordle.wordlemania.Entity.FriendRequest;
import com.wordle.wordlemania.Model.FriendRequestId;
import com.wordle.wordlemania.Model.FriendStatus;

public interface FriendReqRepositories extends CrudRepository<FriendRequest, FriendRequestId> {

    List<FriendRequest> findAllByReceiverIdAndStatus(Integer receiverId, FriendStatus status);

    // List<FriendRequest> findAllByReceiverIdAndStatusOrSenderIdAndStatus(Integer
    // receiverId, FriendStatus status1, Integer senderId, FriendStatus status2);

    @Query("SELECT fr FROM FriendRequest fr " +
            "JOIN fr.receiver r " +
            "JOIN fr.sender s " +
            "WHERE (r.id = :receiverId AND fr.status = :status1) OR (s.id = :senderId AND fr.status = :status2) " +
            "ORDER BY CASE WHEN r.status = 'Online' THEN 0 " +
            "              WHEN r.status = 'Playing' THEN 1 " +
            "              ELSE 2 END, " +
            "         CASE WHEN s.status = 'Online' THEN 0 " +
            "              WHEN s.status = 'Playing' THEN 1 " +
            "              ELSE 2 END")
    List<FriendRequest> findAllSortedByPlayerStatus(
            @Param("receiverId") Integer receiverId,
            @Param("status1") FriendStatus status1,
            @Param("senderId") Integer senderId,
            @Param("status2") FriendStatus status2);

    boolean existsByReceiverIdAndSenderIdAndStatus(Integer receiverId, Integer senderId, FriendStatus status);
}