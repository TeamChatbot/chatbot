package de.morpheus.chatbot.init;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.alicebot.ab.AIMLProcessor;
import org.alicebot.ab.AIMLProcessorExtension;
import org.alicebot.ab.Bot;
import org.alicebot.ab.Chat;

import de.morpheus.chatbot.brain.io.datasource.DataSourceFile;
import de.morpheus.chatbot.extension.AIMLExtensionHub;
import de.morpheus.chatbot.extension.ExtensionChatbotBrainSetAndGet;
import de.morpheus.chatbot.extension.ExtensionChatbotRandomTopic;
import de.morpheus.chatbot.extension.ExtensionChatbotRandomValueOfTopic;
import de.morpheus.chatbot.extension.ExtensionCurrentDate;
import de.morpheus.chatbot.extension.ExtensionIsKnown;
import de.morpheus.chatbot.model.brain.ModelChatbotBrain;

public class ConsoleBot {
	
	private Bot bot;
	private Chat chatSession;
	
	public static void main(String[] args) throws IOException{
		ConsoleBot bot = new ConsoleBot(
				new ExtensionChatbotBrainSetAndGet(),
				new ExtensionChatbotRandomTopic(),
				new ExtensionChatbotRandomValueOfTopic(),
				new ExtensionCurrentDate(),
				new ExtensionIsKnown()
		);
		ModelChatbotBrain.getInstance().init(new DataSourceFile());
		bot.startConversation();
	}
	
	public ConsoleBot() {
		this.init();
	}
	
	public ConsoleBot(AIMLProcessorExtension... extensions) {
		this();
		AIMLProcessor.extension = AIMLExtensionHub.createFromExtensions(extensions);	
	}
	
	private void startConversation() {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		while(true){
			try {
//				SilenceTimer silenceTimer = new SilenceTimer();
//				silenceTimer.start(chatSession);
				String input = reader.readLine();
//				silenceTimer.cancel();
				String response = processInput(input);
				System.out.println(response);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void init() {
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
