package de.morpheus.chatbot.extension;

import java.util.Set;

import org.alicebot.ab.AIMLProcessorExtension;
import org.alicebot.ab.ParseState;
import org.alicebot.ab.Utilities;
import org.w3c.dom.Node;

import de.morpheus.chatbot.model.brain.ModelChatbotBrain;
import de.morpheus.chatbot.utility.UtilityAIML;

/**
 * This Extension can only be used within the <name/> tag of a <condition/> tag 
 * It checks whether the requested information exists in database.
 * Then it stores the boolean value in the bot predicates, which then
 * can be used in as values in <li/> tags.
 * @author Blizzard Postapex
 *
 */
public class ExtensionIsKnown implements AIMLProcessorExtension {
	
	Set<String> tagSet = Utilities.stringSet("isKnown");
	
	static final String PREDICATE = "lastEvaluatedIsKnownValue";

	@Override
	public Set<String> extensionTagSet() {
		return tagSet;
	}

	@Override
	public String recursEval(Node node, ParseState ps) {
		String nodeName = node.getNodeName();
        assert(nodeName.equals("isKnown"));
        
        String category = UtilityAIML.getAttributeOrTagValue(node, ps, "category");
		String topic = UtilityAIML.getAttributeOrTagValue(node, ps, "topic");
		boolean condition = ModelChatbotBrain.getInstance().containsKeyByCategory(category, topic);
		
		String str = "undefined";
		if(condition) {
			str = ModelChatbotBrain.getInstance().get(category, topic).get(0);
		}
		
		ps.chatSession.predicates.put(PREDICATE, str);
		
		/* Okay this does not return true or false.
		 * But instead the predicate name to check against.
		 * Much easier than to get and set the predicate in AIML all the time.
		 */
		return PREDICATE;
	}
}
