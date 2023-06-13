package com.wordle.wordlemania.Repos;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.wordle.wordlemania.Entity.User;

public interface UserRepositories extends CrudRepository<User, Integer> {

    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM User u WHERE u.userGuest.id = :guestId")
    boolean existsByGuestId(int guestId);

    public Optional<User> findByEmail(String email); 

    public List<User> findTop3ByOrderByScoreDescTotalWinDescTotalPlayDesc();
}