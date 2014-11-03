package de.morpheus.chatbot.aiml;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.morpheus.chatbot.model.Content;
import de.morpheus.chatbot.model.ModelChatbotBrain;

public class AimlFileBrainInterpreter implements IAIMLFileInterpreter {
	
	public ModelChatbotBrain interprete(List<String> lstAimlFileLine, String fileName){
		ModelChatbotBrain returnValue = new ModelChatbotBrain();
		for(String currentLine : lstAimlFileLine){
			String[] split = currentLine.split(":");
			if(split.length == 2){
				String key = split[0];
				String value = split[1];
				String category = fileName.split("\\.")[0];
				Content content = new Content();
				if(!returnValue.containsKey(category)){
					Map<String, List<Content>> innerArrayList = new HashMap<String, List<Content>>();
					returnValue.put(category, innerArrayList);
				}
				if(!returnValue.get(category).containsKey(key)){
					returnValue.get(category).put(key, new ArrayList<Content>());
				}
				content.setDate(new Date());
				content.setValue(value);
				returnValue.get(category).get(key).add(content);
			}
			
		}
		return returnValue;
		
	}
}
