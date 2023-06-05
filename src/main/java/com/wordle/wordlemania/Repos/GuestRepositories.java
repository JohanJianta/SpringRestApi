package com.wordle.wordlemania.Repos;

import org.springframework.data.repository.CrudRepository;

import com.wordle.wordlemania.Entity.Guest;

public interface GuestRepositories extends CrudRepository<Guest, Integer> {
}
