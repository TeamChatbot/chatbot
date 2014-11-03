package de.morpheus.chatbot.aiml.io;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import de.morpheus.chatbot.aiml.AIMLInputOutput;
import de.morpheus.chatbot.model.Content;
import de.morpheus.chatbot.model.ModelChatbotBrain;

public class AimlBufferedWriter extends AIMLInputOutput {
	
	private BufferedWriter bufferedWriter;
	
	public AimlBufferedWriter(ModelChatbotBrain brainModel) {
		for(Entry<String, Map<String, List<Content>>> currentEntry : brainModel.entrySet()){
			String category = currentEntry.getKey();
			File file = super.getAimlFileByCategory(category).toFile();
			try{
				if(!file.exists()){
					file.createNewFile();
				}
				this.bufferedWriter = new BufferedWriter(new FileWriter(file));
				for(Map.Entry<String, List<Content>> currentModelCategory : currentEntry.getValue().entrySet()){
					for(Content currentContent : currentModelCategory.getValue()){
						this.bufferedWriter.write(currentModelCategory.getKey() + ":" + currentContent.getValue());
						this.bufferedWriter.newLine();
					}
				}
				this.bufferedWriter.flush();
			} catch (IOException e) {
				e.printStackTrace();
			} finally{
				
			}
		}
	}
}
