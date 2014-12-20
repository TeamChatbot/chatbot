package de.morpheus.chatbot.extension;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import org.alicebot.ab.AIMLProcessor;
import org.alicebot.ab.AIMLProcessorExtension;
import org.alicebot.ab.ParseState;
import org.alicebot.ab.Utilities;
import org.w3c.dom.Node;

import de.morpheus.chatbot.model.brain.ModelChatbotBrain;
import de.morpheus.chatbot.utility.UtilityRandom;

public class ExtensionChatbotRandomValueOfTopic implements AIMLProcessorExtension  {

	public Set<String> extensionTagNames = Utilities.stringSet("brain.random.value");
    
    public Set <String> extensionTagSet() {
        return extensionTagNames;
    }

    public String recursEval(Node node, ParseState ps) {
        try {
            String nodeName = node.getNodeName();
            if (nodeName.equals("brain.random.value")){
            	return this.selectValue(node, ps);
            }else{
            	return (AIMLProcessor.genericXML(node, ps));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return "";
        }
    }

    private String selectValue(Node node, ParseState ps) {
    	String lastSelectedValue = ExtensionChatbotBrainSetAndGet.LAST_SELECTED_VALUE;
    	if(lastSelectedValue == null){
	    	String[] topicSplit = ps.topic.split("\\.");
			String category = topicSplit[1];
			String topic = topicSplit[2];
			try{
				if(category == null || topic == null){
					throw new IOException();
				}	
			}catch(IOException e){
				System.out.println("Es ist kein Topic gesetzt");
			}
			if(category != null && topic != null){
				List<String> resultList = ModelChatbotBrain.getInstance().get(category, topic);
				if(resultList != null && resultList.size() > 0){
					int randomIndex = UtilityRandom.random(resultList.size());
					return resultList.get(randomIndex);
				}
			}
    	}else{
    		ExtensionChatbotBrainSetAndGet.LAST_SELECTED_VALUE = null;
    		return lastSelectedValue;
    	}
		return null;
	}
}
