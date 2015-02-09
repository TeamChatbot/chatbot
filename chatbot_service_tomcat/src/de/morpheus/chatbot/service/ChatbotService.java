package de.morpheus.chatbot.service;

import javax.jws.*;




import de.morpheus.chatbot.model.BotInit;
@WebService
public class ChatbotService {

	private BotInit chatbot = new BotInit();
	
	@WebMethod
	public String communicate(String input) {
	    return this.chatbot.processInput(input);
	}
}