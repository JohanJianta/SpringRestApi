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

	// join chatroom
	@MessageMapping("/chat.register")
	public ChatMessage register(@Payload ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor) {
		simpMessagingTemplate.convertAndSendToUser(Integer.toString(chatMessage.getGameCode()), "/chatroom", chatMessage);
		return chatMessage;
	}

	// send message to chatroom
	@MessageMapping("/chat.send")
	public ChatMessage sendMessage(@Payload ChatMessage chatMessage) {
		simpMessagingTemplate.convertAndSendToUser(Integer.toString(chatMessage.getGameCode()), "/chatroom", chatMessage);
		return chatMessage;
	}

	// send answer to chatroom
	@MessageMapping("/game.answer")
	public ChatMessage sendAnswer(@Payload ChatMessage chatMessage) {
		simpMessagingTemplate.convertAndSendToUser(Integer.toString(chatMessage.getGameCode()), "/answer", chatMessage);
		return chatMessage;
	}

	// send ready vote to chatroom
	@MessageMapping("/game.ready")
	public ChatMessage sendVote(@Payload ChatMessage chatMessage) {
		simpMessagingTemplate.convertAndSendToUser(Integer.toString(chatMessage.getGameCode()), "/ready", chatMessage);
		return chatMessage;
	}

	// send invitation to specific player
	@MessageMapping("/invite")
	public ChatMessage sendInvitation(@Payload ChatMessage chatMessage) {
		simpMessagingTemplate.convertAndSendToUser(Integer.toString(chatMessage.getGuestId()), "/request", chatMessage);
		return chatMessage;
	}

}
