package de.morpheus.chatbot.extension;

import java.util.Set;

import org.alicebot.ab.AIMLProcessor;
import org.alicebot.ab.AIMLProcessorExtension;
import org.alicebot.ab.ParseState;
import org.alicebot.ab.Utilities;
import org.w3c.dom.Node;

import de.morpheus.chatbot.aiml.io.AimlBufferedWriter;
import de.morpheus.chatbot.model.Brain;
import de.morpheus.chatbot.utility.AIMLUtility;

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
		String topic = AIMLUtility.getAttributeOrTagValue(node, ps, "topic");
		String category = AIMLUtility.getAttributeOrTagValue(node, ps, "category");
		Boolean multiple = (Boolean.valueOf(AIMLUtility.getAttributeOrTagValue(node, ps, "multiple")));
		Brain.getInstance().add(category, topic, value, multiple);
		ps.topic = String.format(ExtensionChatbotRandomTopic.SMALLTALK_TOPIC_PATTERN, category, topic);
		ps.chatSession.predicates.put("topic", ps.topic);
		new AimlBufferedWriter(Brain.getInstance());
		LAST_SELECTED_VALUE = value.trim();
		return "";
	}

	private String tagGet(Node node, ParseState ps) {
		String topic = AIMLUtility.getAttributeOrTagValue(node, ps, "topic");
		String category = AIMLUtility.getAttributeOrTagValue(node, ps, "category");
		int index = 0; //TODO: Do something about index.
		return Brain.getInstance().get(category, topic).get(index).getValue();
	}

}
