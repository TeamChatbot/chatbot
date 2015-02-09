package de.morpheus.chatbot.service;

import javax.jws.*;
import de.morpheus.chatbot.base.Chatbot;

@WebService
public class ChatbotService {
	
	private Chatbot chatbot = new Chatbot();
	
	@WebMethod
	public String communicate(String input) {
	    return this.chatbot.processInput(input);
	}
}