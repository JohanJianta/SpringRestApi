package com.wordle.wordlemania.Repos;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.wordle.wordlemania.Entity.Word;

public interface WordRepositories extends CrudRepository<Word, Long> {
    // @Query(value = "SELECT word FROM word WHERE LENGTH(word) = :length ORDER BY RAND() LIMIT 1", nativeQuery = true)
    // String findRandomByLength(int length);

    @Query(value = "SELECT w FROM Word w WHERE LENGTH(w.word) = :length ORDER BY RAND() LIMIT 1", nativeQuery = false)
    Word findRandomByLength(int length);

    Boolean existsByWord(String word);
}