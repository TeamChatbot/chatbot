package de.morpheus.chatbot.extension;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.alicebot.ab.AIMLProcessorExtension;
import org.alicebot.ab.ParseState;
import org.w3c.dom.Node;

/**
 * Doesn't have constructors. Instead use factory methods.
 * @author Blizzard Postapex
 *
 */
public class AIMLExtensionHub implements AIMLProcessorExtension {
	
	private final List<AIMLProcessorExtension> extensions = new ArrayList<AIMLProcessorExtension>();
	
	private AIMLExtensionHub() {}
	
	public static AIMLExtensionHub createFromExtensions(List<AIMLProcessorExtension> extensions) {
		AIMLExtensionHub ret = new AIMLExtensionHub();
		ret.extensions.addAll(extensions);
		return ret;
	}
	
	public static AIMLExtensionHub createFromExtensions(AIMLProcessorExtension... extensions) {
		AIMLExtensionHub ret = new AIMLExtensionHub();
		for(AIMLProcessorExtension extension : extensions) {
			for(AIMLProcessorExtension temp : ret.extensions) {
				for(String str : extension.extensionTagSet()) {
					if(temp.extensionTagSet().contains(str)) {
						System.err.println("Warning: Multiple occurence of tag: " + str);
					}
				}
			}
			ret.extensions.add(extension);
		}
		return ret;
	}
		
	@Override
	public Set<String> extensionTagSet() {
		Set<String> set = new TreeSet<String>();
		for(AIMLProcessorExtension extension : extensions) {
			set.addAll(extension.extensionTagSet());
		}
		return set;
	}

	@Override
	public String recursEval(Node node, ParseState ps) {
		String ret = null;
		
		for(AIMLProcessorExtension extension : extensions) {
			if(extension.extensionTagSet().contains(node.getNodeName())) {
				if(ret != null) {
					System.err.println("Warning: Overwriting return message " + ret);
					System.err.println(getClass().getName() + " contains multiple occurences of tag " + node.getNodeName());
					System.err.println("Latest occurence of tag in class " + extensions.getClass().getName());
				}
				ret = extension.recursEval(node, ps);
			}
		}
		
		return ret;
	}
	
}
