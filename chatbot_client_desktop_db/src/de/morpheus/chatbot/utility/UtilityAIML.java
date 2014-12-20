package de.morpheus.chatbot.utility;

import org.alicebot.ab.AIMLProcessor;
import org.alicebot.ab.ParseState;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public final class UtilityAIML {
	
	private UtilityAIML() {}
	
    public static String getAttributeOrTagValue (Node node, ParseState ps, String attributeName) {
        String result = "";
        Node m = node.getAttributes().getNamedItem(attributeName);
        if (m == null) {
            NodeList childList = node.getChildNodes();
            result = null;         // no attribute or tag named attributeName
            for (int i = 0; i < childList.getLength(); i++)   {
                Node child = childList.item(i);
                if (child.getNodeName().equals(attributeName)) {
                    result = AIMLProcessor.evalTagContent(child, ps, null);
                }
            }
        }
        else {
            result = m.getNodeValue();
        }
        return result;
    }
}
