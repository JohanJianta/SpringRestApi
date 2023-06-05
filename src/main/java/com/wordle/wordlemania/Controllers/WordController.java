package com.wordle.wordlemania.Controllers;

import com.wordle.wordlemania.Entity.Word;
import com.wordle.wordlemania.Services.WordService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/Word")
public class WordController {

    @Autowired
    private WordService wordService;

    @PostMapping
    public ResponseEntity<Boolean> checkExist(@RequestBody Word word) {
        if (word.getWord().length() < 5 || word.getWord().length() > 10) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false);
        } else {
            return ResponseEntity.ok().body(wordService.isExist(word.getWord()));
        }
    }
}
