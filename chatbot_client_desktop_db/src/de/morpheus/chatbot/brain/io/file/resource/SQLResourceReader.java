package de.morpheus.chatbot.brain.io.file.resource;

import java.io.IOException;
import java.io.InputStream;

public class SQLResourceReader {

	InputStream inputStream = null;
	
	public SQLResourceReader(Resource resource){
		ClassLoader classLoader = getClass().getClassLoader();
		inputStream = classLoader.getResourceAsStream(resource.getPath() + "/" + resource.getName());
	}
	
	public String read(){
		StringBuffer statement = new StringBuffer();
		try {
			if(this.inputStream != null){
				int character = this.inputStream.read();
				while(character != -1){
					statement.append((char)character);
					character = this.inputStream.read();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return statement.toString();
	}

}
