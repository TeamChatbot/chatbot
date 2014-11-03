package de.morpheus.chatbot.aiml;

import java.util.List;
import java.util.Map;

import de.morpheus.chatbot.model.Content;

public interface IAIMLFileInterpreter {

	public Map<String, Map<String, List<Content>>> interprete(List<String> lstAimlFileLine, String fileName);
	
}
