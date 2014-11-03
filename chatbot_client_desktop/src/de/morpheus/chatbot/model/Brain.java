package de.morpheus.chatbot.model;


//TODO: Move this to ModelChatbotBrain class

public class Brain {

	private static ModelChatbotBrain modelChatBotBrain = new ModelChatbotBrain();
	
	private Brain(){}
	
	public static ModelChatbotBrain getInstance(){
		return Brain.modelChatBotBrain;
	}
	
}
