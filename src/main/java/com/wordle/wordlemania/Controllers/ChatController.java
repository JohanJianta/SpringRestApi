package com.wordle.wordlemania.Controllers;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

import com.wordle.wordlemania.Utils.RandomWordGenerator;
import com.wordle.wordlemania.dto.ChatMessage;
import com.wordle.wordlemania.dto.ChatMessage.MessageType;

@Controller
public class ChatController {

	private int playerCount = 0;
	private String keyword = "";
	private CompletableFuture<String> futureWord = RandomWordGenerator.getRandomWord();

	@MessageMapping("/chat.register")
	@SendTo("/topic/public")
	public ChatMessage register(@Payload ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor) {
		headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
		return chatMessage;
	}

	@MessageMapping("/chat.send")
	@SendTo("/topic/public")
	public ChatMessage sendMessage(@Payload ChatMessage chatMessage) {
		if(chatMessage.getType() == MessageType.LEAVE){
			playerCount--;
		}
		return chatMessage;
	}

	@MessageMapping("/player.count")
	@SendTo("/topic/keyword")
	public String sendWord() {
		playerCount++;
		if (playerCount == 4) {
			// Wait for the asynchronous operation to complete (optional)
			futureWord.thenAccept(word -> {
			});
			try {
				keyword = futureWord.get();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
			futureWord.join();
			return keyword;
		} else {
			return playerCount + " player in room, " + (4-playerCount)+ " player remaining";
		}
	}

	@MessageMapping("/word")
	@SendTo("/topic/answer")
	public String sendWord(@Payload String validWord) {
		return validWord;
	}

}
