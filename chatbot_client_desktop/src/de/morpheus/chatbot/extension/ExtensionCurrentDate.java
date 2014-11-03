package de.morpheus.chatbot.extension;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

import org.alicebot.ab.AIMLProcessor;
import org.alicebot.ab.AIMLProcessorExtension;
import org.alicebot.ab.ParseState;
import org.alicebot.ab.Utilities;
import org.w3c.dom.Node;

public class ExtensionCurrentDate implements AIMLProcessorExtension {
	
    public Set<String> extensionTagNames = Utilities.stringSet("currentDate", "expression");
    
    public Set <String> extensionTagSet() {
        return extensionTagNames;
    }
    
	private String tagCurrentDate(Node node, ParseState ps) {
		SimpleDateFormat format = new SimpleDateFormat("dd.mm.yyyy hh:mm:ss");
		return format.format(new Date());
	}
	
	private String tagExpression(Node node, ParseState ps){
		//ps.chatSession.bot.properties.
		return null;
	}

    public String recursEval(Node node, ParseState ps) {
        try {
            String nodeName = node.getNodeName();
            if (nodeName.equals("currentDate")){
                return tagCurrentDate(node, ps);
            }else if (nodeName.equals("expression")){
                return tagExpression(node, ps);
            }else {
            	return (AIMLProcessor.genericXML(node, ps));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return "";
        }
    }



}
