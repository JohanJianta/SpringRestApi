package com.wordle.wordlemania.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatMessage {
    private String content;
	private String sender;
	private MessageType type;
	private Integer gameCode;

	public enum MessageType {
		CHAT, LEAVE, JOIN
	}
}
