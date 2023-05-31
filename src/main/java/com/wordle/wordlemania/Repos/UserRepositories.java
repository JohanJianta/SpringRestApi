package com.wordle.wordlemania.Repos;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.wordle.wordlemania.Entity.User;

public interface UserRepositories extends CrudRepository<User, Integer>, PagingAndSortingRepository<User, Integer> {

    public Optional<User> findByEmail(String email); 

    public List<User> findTop3ByOrderByScoreDescTotalWinDescTotalPlayAsc();
}