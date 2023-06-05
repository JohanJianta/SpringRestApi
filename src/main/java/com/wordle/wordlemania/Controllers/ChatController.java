package com.wordle.wordlemania.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.wordle.wordlemania.dto.ChatMessage;

@Controller
public class ChatController {

	@Autowired
	private SimpMessagingTemplate simpMessagingTemplate;

	@MessageMapping("/chat.register")
	public ChatMessage register(@Payload ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor) {
		// chatMessage.setPlayerCount(historyController.findPlayercount(chatMessage.getRoomId()));
		simpMessagingTemplate.convertAndSendToUser(Integer.toString(chatMessage.getGameCode()), "/chatroom", chatMessage);
		return chatMessage;
	}

	@MessageMapping("/chat.send")
	public ChatMessage sendMessage(@Payload ChatMessage chatMessage) {
		simpMessagingTemplate.convertAndSendToUser(Integer.toString(chatMessage.getGameCode()), "/chatroom", chatMessage);
		return chatMessage;
	}

	// @MessageMapping("/game.keyword")
	// public String sendKeyword(@Payload ChatMessage chatMessage) {
	// 	String word = null;
	// 	if (chatMessage.getRequestedLength() < 5 || chatMessage.getRequestedLength() > 10) {
	// 		word = "Requested word length is not allowed";
	// 	} else {
	// 		word = wordController.getRandomWord(chatMessage.getRequestedLength());
	// 	}
	// 	simpMessagingTemplate.convertAndSendToUser(Integer.toString(chatMessage.getRoomId()), "/setting", word);
	// 	return word;
	// }

	@MessageMapping("/game.answer")
	public ChatMessage sendAnswer(@Payload ChatMessage chatMessage) {
		simpMessagingTemplate.convertAndSendToUser(Integer.toString(chatMessage.getGameCode()), "/answer", chatMessage);
		return chatMessage;
	}

}
