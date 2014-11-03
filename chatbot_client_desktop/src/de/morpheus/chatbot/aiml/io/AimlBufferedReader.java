package de.morpheus.chatbot.aiml.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public class AimlBufferedReader extends BufferedReader {

	public AimlBufferedReader(Reader in) {
		super(in);
	}
	
	public List<String> readAimlFile(){
		String zeile = null;
		List<String> returnValue = new ArrayList<String>();
		try {
			while ((zeile = super.readLine()) != null) {
				returnValue.add(zeile);
			}
			this.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return returnValue;
	}

}
