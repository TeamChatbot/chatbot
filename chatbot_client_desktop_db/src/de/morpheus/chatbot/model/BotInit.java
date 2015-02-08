package de.morpheus.chatbot.model;

import org.alicebot.ab.Bot;
import org.alicebot.ab.Chat;

import de.morpheus.chatbot.model.brain.ModelChatbotBrain;

public class BotInit {
	
	
	private Bot bot;
	private Chat chatSession;
	
	
	public BotInit()
	{
		this.init();
	}

	public void init() {
		this.bot 			= new Bot(ModelChatbotBrain.BOT_NAME, "C:\\");
		this.chatSession 	= new Chat(bot);
		
	}
	
	
	
	public String processInput(String input) throws IllegalStateException {
		if(bot == null){
			throw new IllegalStateException("Bot hasn't been initialized yet!");
		}
		return chatSession.multisentenceRespond(input);
	}
	
	
	
	
	
}
