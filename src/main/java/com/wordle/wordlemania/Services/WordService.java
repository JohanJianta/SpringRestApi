package com.wordle.wordlemania.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wordle.wordlemania.Repos.WordRepositories;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class WordService {

    @Autowired
    private WordRepositories wordRepository;

    public boolean isExist (String word) {
        return wordRepository.existsByWord(word);
    }
}

