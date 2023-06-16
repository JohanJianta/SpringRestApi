package com.wordle.wordlemania.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ChatMessage {
    private String content;
	private String sender;
	private MessageType type;
	private Integer gameCode;
	private Integer guestId;

	public enum MessageType {
		CHAT, LEAVE, JOIN
	}
}
