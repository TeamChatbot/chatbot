package de.morpheus.chatbot.extension;

import java.util.Set;

import org.alicebot.ab.AIMLProcessor;
import org.alicebot.ab.AIMLProcessorExtension;
import org.alicebot.ab.ParseState;
import org.alicebot.ab.Utilities;
import org.w3c.dom.Node;

import de.morpheus.chatbot.model.brain.ModelChatbotBrain;
import de.morpheus.chatbot.utility.UtilityRandom;

public class ExtensionChatbotRandomTopic implements AIMLProcessorExtension  {

	public static String SMALLTALK_TOPIC_PATTERN = "smalltalk.%s.%s"; 
	
	public Set<String> extensionTagNames = Utilities.stringSet("brain.random.topic");
    
    public ExtensionChatbotRandomTopic(){
		
	}
    
    public Set <String> extensionTagSet() {
        return extensionTagNames;
    }

    public String recursEval(Node node, ParseState ps) {
        try {
            String nodeName = node.getNodeName();
            if (nodeName.equals("brain.random.topic")){
            	return this.selectRandomTopic(node, ps);
            }else{
            	return (AIMLProcessor.genericXML(node, ps));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return "";
        }
    }

	private String selectRandomTopic(Node node, ParseState ps) {
		String categoryValue 	= null;
		String topicValue 	= null;
		if(ModelChatbotBrain.getInstance().keySet().size() > 0){
			int randomCategoryIndex = UtilityRandom.random(ModelChatbotBrain.getInstance().keySet().size());
			categoryValue = ModelChatbotBrain.getInstance().getCategoryByIndex(randomCategoryIndex);
		}
		if(categoryValue != null){
			int randomTopicIndex = UtilityRandom.random(ModelChatbotBrain.getInstance().get(categoryValue).size());
			topicValue = ModelChatbotBrain.getInstance().get(categoryValue).keySet().toArray()[randomTopicIndex].toString();
		}
		if(topicValue != null){
			ps.topic = String.format(SMALLTALK_TOPIC_PATTERN, categoryValue, topicValue);
			ps.chatSession.predicates.put("topic", ps.topic);
		}
		return "";
	}
}
