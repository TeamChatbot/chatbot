package de.morpheus.chatbot.init;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.alicebot.ab.AIMLProcessor;
import org.alicebot.ab.AIMLProcessorExtension;
import org.alicebot.ab.Bot;
import org.alicebot.ab.Chat;

import de.morpheus.chatbot.brain.io.datasource.DataSourceDB;
import de.morpheus.chatbot.brain.io.datasource.DataSourceFile;
import de.morpheus.chatbot.extension.AIMLExtensionHub;
import de.morpheus.chatbot.extension.ExtensionChatbotBrainSetAndGet;
import de.morpheus.chatbot.extension.ExtensionChatbotRandomTopic;
import de.morpheus.chatbot.extension.ExtensionChatbotRandomValueOfTopic;
import de.morpheus.chatbot.extension.ExtensionCurrentDate;
import de.morpheus.chatbot.extension.ExtensionIsKnown;
import de.morpheus.chatbot.model.BotInit;
import de.morpheus.chatbot.model.brain.ModelChatbotBrain;

public class ConsoleBot {
	
	private BotInit bot;
	
	public static void main(String[] args) throws IOException{
		ConsoleBot bot = new ConsoleBot();
	
		bot.startConversation();
	}
	
	
	public ConsoleBot() {
		this.bot= new BotInit(new DataSourceDB(),BotInit.DEFAULT_AIML_EXTENSIONS);
		
	}
	
	private void startConversation() {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		while(true){
			try {
//				SilenceTimer silenceTimer = new SilenceTimer();
//				silenceTimer.start(chatSession);
				String input = reader.readLine();
//				silenceTimer.cancel();
				String response = bot.processInput(input);
				System.out.println(response);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	
	
}
