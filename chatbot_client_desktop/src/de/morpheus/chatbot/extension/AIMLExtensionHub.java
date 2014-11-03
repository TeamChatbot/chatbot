package de.morpheus.chatbot.extension;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.alicebot.ab.AIMLProcessorExtension;
import org.alicebot.ab.ParseState;
import org.w3c.dom.Node;

import de.morpheus.chatbot.aiml.AIMLInputOutput;
import de.morpheus.chatbot.aiml.io.String_to_CSV_Writer;

/**
 * Doesn't have constructors. Instead use factory methods.
 * @author Blizzard Postapex
 *
 */
public class AIMLExtensionHub implements AIMLProcessorExtension {
	
	private final List<AIMLProcessorExtension> extensions = new ArrayList<AIMLProcessorExtension>();
	
	public static final String CLASS_FILE_NAME = "Extensions.csv";
	public static final String DIRECTORY_PATH = java.nio.file.Paths.get(AIMLInputOutput.AIML_FILE_DIRECTORY.toString(), "..", "temp").toString();
	
	private AIMLExtensionHub() {}
	
	//factory methods
	public static AIMLExtensionHub createFromExtensions(AIMLProcessorExtension... extensions) {
		AIMLExtensionHub ret = new AIMLExtensionHub();
		for(AIMLProcessorExtension extension : extensions) {
			
			//Checking for doubles
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
	
	public static AIMLExtensionHub createFromExtensions(List<AIMLProcessorExtension> extensions) {
		AIMLExtensionHub ret = new AIMLExtensionHub();
		ret.extensions.addAll(extensions);
		return ret;
	}
	
	public static AIMLExtensionHub createFromClassNames(String... strings) {
		List<AIMLProcessorExtension> list = new ArrayList<AIMLProcessorExtension>();
		for(String className : strings) {
			AIMLProcessorExtension extension;
			try {
				extension = (AIMLProcessorExtension) Class.forName(className).newInstance();
				list.add(extension);
			} catch (InstantiationException | IllegalAccessException
					| ClassNotFoundException e) {
				e.printStackTrace();
			}	
		}
		return createFromExtensions(list);
	}
	
	public static AIMLExtensionHub createFromPath(String path, String fileName) throws IOException {
		//Just an example, you can always switch another in.
		String_to_CSV_Writer writer = new String_to_CSV_Writer(path, fileName);
		return createFromClassNames(Arrays.copyOf(writer.restoreAll().toArray(), writer.getStoredCount(), String[].class));
	}

	//inherited methods
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
	
	//Testing methods
	public static void exportClasses(Set<Class<? extends AIMLProcessorExtension>> classes) throws IOException {
		String_to_CSV_Writer writer = new String_to_CSV_Writer(DIRECTORY_PATH, CLASS_FILE_NAME);
		writer.clearStorage();
		for(Class<? extends AIMLProcessorExtension> cls : classes) {
			writer.store(cls.getName());
		}
	}
	
	public static void main(String[] args) throws IOException {
		//Write out necessary Class files to be loaded in the hub.
		Set<Class<? extends AIMLProcessorExtension>> classes =
				new HashSet<Class<? extends AIMLProcessorExtension>>();
		
		classes.add(ExtensionChatbotBrainSetAndGet.class);
		classes.add(ExtensionChatbotRandomTopic.class);
		classes.add(ExtensionChatbotRandomValueOfTopic.class);
		classes.add(ExtensionCurrentDate.class);
		classes.add(ExtensionIsKnown.class);
		
		exportClasses(classes);
		
		AIMLExtensionHub hub = AIMLExtensionHub.createFromPath(DIRECTORY_PATH, CLASS_FILE_NAME);
		
		System.out.println(hub.extensionTagSet());
		
	}

}
