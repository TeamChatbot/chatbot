package de.morpheus.chatbot.model;

import org.alicebot.ab.AIMLProcessor;
import org.alicebot.ab.AIMLProcessorExtension;
import org.alicebot.ab.Bot;
import org.alicebot.ab.Chat;

import de.morpheus.chatbot.brain.io.datasource.DataSource;
import de.morpheus.chatbot.brain.io.datasource.DataSourceDB;
import de.morpheus.chatbot.extension.AIMLExtensionHub;
import de.morpheus.chatbot.extension.ExtensionChatbotBrainSetAndGet;
import de.morpheus.chatbot.extension.ExtensionChatbotRandomTopic;
import de.morpheus.chatbot.extension.ExtensionChatbotRandomValueOfTopic;
import de.morpheus.chatbot.extension.ExtensionCurrentDate;
import de.morpheus.chatbot.extension.ExtensionIsKnown;
import de.morpheus.chatbot.model.brain.ModelChatbotBrain;

public class BotInit {
	
	public static AIMLProcessorExtension[] DEFAULT_AIML_EXTENSIONS={
		new ExtensionChatbotBrainSetAndGet(),
		new ExtensionChatbotRandomTopic(),
		new ExtensionChatbotRandomValueOfTopic(),
		new ExtensionCurrentDate(),
		new ExtensionIsKnown()};
	
	private Bot bot;
	private Chat chatSession;
	
	
	public BotInit(DataSource datasource)
	{
		this.init(datasource,DEFAULT_AIML_EXTENSIONS);
		
	}

	public void init(DataSource datasource,AIMLProcessorExtension... extensions) {
		ModelChatbotBrain.getInstance().init(datasource);	
		AIMLProcessor.extension = AIMLExtensionHub.createFromExtensions(extensions);	
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
