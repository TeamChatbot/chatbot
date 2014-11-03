package de.morpheus.chatbot.base;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.alicebot.ab.AIMLProcessor;
import org.alicebot.ab.Bot;
import org.alicebot.ab.Chat;

import de.morpheus.chatbot.aiml.AIMLInputOutput;
import de.morpheus.chatbot.extension.AIMLExtensionHub;
import de.morpheus.chatbot.processing.abstracts.InputProcessor;

public class Chatbot implements InputProcessor<String, String> {

	public static final String BOT_NAME = "Andre";
	
	private Bot bot;
	private Chat chatSession;
	private AIMLInputOutput aimlInputOutput = new AIMLInputOutput();
	
	public static void main(String[] args) throws IOException{
		new Chatbot().startConversation();
	}
	
	public Chatbot() {
		try {
			AIMLProcessor.extension = AIMLExtensionHub.createFromPath(AIMLExtensionHub.DIRECTORY_PATH, AIMLExtensionHub.CLASS_FILE_NAME);
			aimlInputOutput.readAimlFiles();
			this.init();
			this.configure();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Chatbot(AIMLExtensionHub hub) {
		this();
		AIMLProcessor.extension = hub;
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
		this.bot 			= new Bot(BOT_NAME, "C:\\");
		this.chatSession 	= new Chat(bot);
		
	}

	private void configure() {
		/*bot.brain.addCategory(new Category(1,"ich bin *", "*", "*", "schoen das du <star/> bist", "expression.aiml"));
		bot.writeQuit();*/
	}

	@Override
	public String processInput(String input) throws IllegalStateException {
		if(bot == null){
			throw new IllegalStateException("Bot hasn't been initialized yet!");
		}
		return chatSession.multisentenceRespond(input);
	}
	
}
