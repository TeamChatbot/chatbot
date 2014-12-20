package de.morpheus.chatbot.extension;

import java.util.List;
import java.util.Set;

import org.alicebot.ab.AIMLProcessor;
import org.alicebot.ab.AIMLProcessorExtension;
import org.alicebot.ab.ParseState;
import org.alicebot.ab.Utilities;
import org.w3c.dom.Node;

import de.morpheus.chatbot.model.brain.ModelChatbotBrain;
import de.morpheus.chatbot.model.brain.ModelChatbotBrainContent;
import de.morpheus.chatbot.utility.UtilityAIML;

public class ExtensionChatbotBrainSetAndGet implements AIMLProcessorExtension  {
    
	public static String LAST_SELECTED_VALUE = null;
	
    @Override
    public Set <String> extensionTagSet() {
    	/* The following procedure is necessary!
    	 * Don't ask me why, but using Utilities.stringSet()
    	 * to make one set with two entries
    	 * results in a wrong set that doesn't match tags correctly anymore.
    	 * My guess is the hashing process is faulty.
    	 */
    	Set<String> set1 = Utilities.stringSet("brain.get");
    	set1.addAll(Utilities.stringSet("brain.set"));
        return set1;
    }
	
    @Override
    public String recursEval(Node node, ParseState ps) {
        try {
            String nodeName = node.getNodeName();
            if (nodeName.equals("brain.set")){
                return tagSet(node, ps);
            }
            else if(nodeName.equals("brain.get")) {
            	return tagGet(node, ps);
            }
            else{
            	return (AIMLProcessor.genericXML(node, ps));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return "";
        }
    }
    
	private String tagSet(Node node, ParseState ps) {
		String value = AIMLProcessor.evalTagContent(node, ps, null);
		String topic = UtilityAIML.getAttributeOrTagValue(node, ps, "topic");
		String category = UtilityAIML.getAttributeOrTagValue(node, ps, "category");
		Boolean multiple = (Boolean.valueOf(UtilityAIML.getAttributeOrTagValue(node, ps, "multiple")));
		value = value.trim();
		ModelChatbotBrainContent modelChatbotBrainContent = new ModelChatbotBrainContent();
		modelChatbotBrainContent.add(topic, value, multiple);
		ModelChatbotBrain.getInstance().add(category, topic, value, multiple);
		ps.topic = String.format(ExtensionChatbotRandomTopic.SMALLTALK_TOPIC_PATTERN, category, topic);
		ps.chatSession.predicates.put("topic", ps.topic);
		ModelChatbotBrain.getInstance().getDatasource().write(category, modelChatbotBrainContent);
		LAST_SELECTED_VALUE = value.trim();
		return "";
	}

	private String tagGet(Node node, ParseState ps) {
		String topic = UtilityAIML.getAttributeOrTagValue(node, ps, "topic");
		String category = UtilityAIML.getAttributeOrTagValue(node, ps, "category");
		List<String> lstContent = ModelChatbotBrain.getInstance().get(category, topic);
		return lstContent.get(0);
	}

}
