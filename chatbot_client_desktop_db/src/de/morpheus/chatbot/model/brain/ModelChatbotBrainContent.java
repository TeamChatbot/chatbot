package de.morpheus.chatbot.model.brain;

import java.util.ArrayList;

public class ModelChatbotBrainContent extends ArrayList<String> {

	private boolean multiple = true;
	
	public ModelChatbotBrainContent(){
		super();
	}
	
	public ModelChatbotBrainContent(int size){
		super(size);
	}
	
	public boolean isMultiple() {
		return multiple;
	}

	public void setMultiple(boolean multiple) {
		this.multiple = multiple;
	}
	
}
