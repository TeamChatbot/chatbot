package de.morpheus.chatbot.interpreter;

import java.util.ArrayList;
import java.util.List;

import de.morpheus.chatbot.model.brain.ModelChatbotBrain;
import de.morpheus.chatbot.model.brain.ModelChatbotBrainContent;

public class InterpreterBrain {
	
	public ModelChatbotBrain interprete(List<String> lstAimlFileLine, String fileName){
		ModelChatbotBrain returnValue = ModelChatbotBrain.getInstance();
		for(String currentLine : lstAimlFileLine){
			String[] split = currentLine.split(":");
			if(split.length == 2){
				String key = split[0];
				String value = split[1];
				String category = fileName.split("\\.")[0];
				String content = new String();
				if(!returnValue.containsKey(category)){
					ModelChatbotBrainContent innerArrayList = new ModelChatbotBrainContent();
					returnValue.put(category, innerArrayList);
				}
				if(!returnValue.get(category).containsKey(key)){
					returnValue.get(category).put(key, new ArrayList<String>());
				}
				content = value;
				returnValue.get(category).get(key).add(content);
			}
			
		}
		return returnValue;
		
	}
}