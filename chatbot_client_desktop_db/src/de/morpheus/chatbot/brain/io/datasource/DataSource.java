package de.morpheus.chatbot.brain.io.datasource;

import de.morpheus.chatbot.model.brain.ModelChatbotBrain;
import de.morpheus.chatbot.model.brain.ModelChatbotBrainTopic;

public interface DataSource {

	public ModelChatbotBrain readAll();
	public void writeAll(ModelChatbotBrain obj);
	public void write(String category, ModelChatbotBrainTopic obj);
	
}
