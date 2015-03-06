package de.morpheus.chatbot.service;

import javax.jws.*;

import de.morpheus.chatbot.brain.io.datasource.DataSourceDB;
import de.morpheus.chatbot.model.BotInit;


@WebService
public class ChatbotService {

	private static BotInit chatbot = new BotInit(new DataSourceDB());
	
	@WebMethod
	public String communicate(String input) {
	    return this.chatbot.processInput(input);
	}
}