package com.wordle.wordlemania.Repos;

import org.springframework.data.repository.CrudRepository;

import com.wordle.wordlemania.Entity.Room;

public interface RoomRepositories extends CrudRepository<Room, Integer> {

}