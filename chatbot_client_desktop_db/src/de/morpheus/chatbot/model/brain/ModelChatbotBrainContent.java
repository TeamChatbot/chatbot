package de.morpheus.chatbot.model.brain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ModelChatbotBrainContent extends HashMap<String, List<String>>{
	
	public boolean multiple = true;
	
	public void add(String key, String value, Boolean multiple){
		if(this.containsKey(key) == false){
			this.put(key, new ArrayList<String>(1));
		}
		if(hasValue(key, value) == false){
			if(multiple){
				this.get(key).add(value);
			}else{
				this.get(key).add(0,value);	
			}
		}
	}
	
	public Boolean hasValue(String topic, String text){
		for(String value : this.get(topic)){
			if(value.equals(text)){
				return true;
			}
		}
		return false;
	}

	public boolean isMultiple() {
		return multiple;
	}

	public void setMultiple(boolean multiple) {
		this.multiple = multiple;
	}
}
