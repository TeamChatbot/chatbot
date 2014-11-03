package de.morpheus.chatbot.aiml;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import de.morpheus.chatbot.aiml.io.AimlBufferedReader;
import de.morpheus.chatbot.base.Chatbot;
import de.morpheus.chatbot.model.Brain;
import de.morpheus.chatbot.utility.FileUtility;

public class AIMLInputOutput {

	public static final String 	AIML_FILE_EXTENSION 	= "brain";
	public static final Path 	AIML_FILE_DIRECTORY 	= Paths.get("/bots", Chatbot.BOT_NAME, "brain");

	public Path getAimlFileByCategory(String category){
		return Paths.get(AIML_FILE_DIRECTORY.toString(), category + "." + AIML_FILE_EXTENSION);
	}
	
	public void readAimlFiles(){
		File[] listOfFiles = FileUtility.readFilesFromDirectory(AIML_FILE_DIRECTORY);
		for (File file : listOfFiles) {
		    if (file.isFile()) {
		    	if(!file.getName().endsWith("csv")){
			    	try(AimlBufferedReader reader = new AimlBufferedReader(new FileReader(file))) {
			    		AimlFileBrainInterpreter aimlFileBrainInterpreter = new AimlFileBrainInterpreter();
			    		Brain.getInstance().putAll(aimlFileBrainInterpreter.interprete(reader.readAimlFile(), file.getName()));
					} catch (IOException e) {
						e.printStackTrace();
					}
		    	}
		    }
		}
	}
	
	
}
