package de.morpheus.chatbot.model.brain;

import java.util.HashMap;

public class ModelChatbotBrainTopic extends HashMap<String, ModelChatbotBrainContent>  {
	
	public void add(String key, String value, Boolean multiple){
		if(this.containsKey(key) == false){
			this.put(key, new ModelChatbotBrainContent(1));
		}
		if(hasValue(key, value) == false){
			if(multiple){
				this.get(key).add(value);
			}else{
				this.get(key).clear();
				this.get(key).add(0,value);	
			}
		}
		this.get(key).setMultiple(multiple);
	}
	
	public Boolean hasValue(String topic, String text){
		for(String value : this.get(topic)){
			if(value.equals(text)){
				return true;
			}
		}
		return false;
	}

}
