package de.morpheus.chatbot.brain.io.datasource;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import de.morpheus.chatbot.brain.io.file.brain.BufferedFileReader;
import de.morpheus.chatbot.interpreter.InterpreterBrain;
import de.morpheus.chatbot.model.brain.ModelChatbotBrain;
import de.morpheus.chatbot.model.brain.ModelChatbotBrainContent;

public class DataSourceFile implements DataSource {

	public static final Path 	DEFAULTPATH 			= Paths.get(System.getProperty("java.class.path").split(File.pathSeparator)[0], "..");
	public static final String 	BRAIN_FILE_EXTENSION 	= "brain";
	public static final Path 	BRAIN_FILE_DIRECTORY 	= Paths.get(DEFAULTPATH.toString(), "bots", ModelChatbotBrain.BOT_NAME, "brain");
	
	@Override
	public ModelChatbotBrain readAll() {
		File[] listOfFiles = this.readFilesFromDirectory(BRAIN_FILE_DIRECTORY);
		for (File file : listOfFiles) {
		    if (file.isFile()) {
		    	if(!file.getName().endsWith("csv")){
			    	try(BufferedFileReader reader = new BufferedFileReader(new FileReader(file))) {
			    		InterpreterBrain aimlFileBrainInterpreter = new InterpreterBrain();
			    		ModelChatbotBrain.getInstance().putAll(aimlFileBrainInterpreter.interprete(reader.readSingleFile(), file.getName()));
					} catch (IOException e) {
						e.printStackTrace();
					}
		    	}
		    }
		}
		return ModelChatbotBrain.getInstance();
	}

	@Override
	public void writeAll(ModelChatbotBrain brainModel) {
		BufferedWriter bufferedWriter = null;
		for(Entry<String, ModelChatbotBrainContent> currentEntry : brainModel.entrySet()){
			String category = currentEntry.getKey();
			File file = this.getPathToBrainFileByCategory(category).toFile();
			try{
				if(!file.exists()){
					file.createNewFile();
				}
				bufferedWriter = new BufferedWriter(new FileWriter(file));
				for(Map.Entry<String, List<String>> currentModelCategory : currentEntry.getValue().entrySet()){
					for(String currentContent : currentModelCategory.getValue()){
						bufferedWriter.write(currentModelCategory.getKey() + ":" + currentContent);
						bufferedWriter.newLine();
					}
				}
				bufferedWriter.flush();
			} catch (IOException e) {
				e.printStackTrace();
			} finally{
				if(bufferedWriter != null){
					try {
						bufferedWriter.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	@Override
	public void write(String category, ModelChatbotBrainContent obj) {
		
	}
	
	
	public File[] readFilesFromDirectory(Path path){
		return path.toFile().listFiles();
	}
	
	
	public Path getPathToBrainFileByCategory(String category){
		return Paths.get(DataSourceFile.BRAIN_FILE_DIRECTORY.toString(), category + "." + DataSourceFile.BRAIN_FILE_EXTENSION);
	}
}
