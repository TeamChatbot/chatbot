package de.morpheus.chatbot.processing;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Arrays;

import de.morpheus.chatbot.processing.abstracts.InputProcessor;

public class AndrésWebService implements InputProcessor<String, String> {

	private final static String webAdress = 
			"http://xsmorpheussx.ddns.net:8080/chatbot_service/ws/chat/message?message=";
	
	@Override
	public String processInput(String input) {
		
		URLConnection connection = null;
		String ret = null;
		try {
			connection = new URL(webAdress + URLEncoder.encode(input, "UTF-8")).openConnection();
			
			try(Reader r = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
				 StringWriter w = new StringWriter();
			     char[] buffer = new char[1024];
			     int n = 0;
			     while ((n = r.read(buffer)) != -1) {
			         w.write(buffer, 0, n);
			     }
			     ret = w.toString();
			     
			} 
			
		} catch (IOException e) {
			e.printStackTrace();
		}
        
		return ret;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(new AndrésWebService().processInput(Arrays.toString(args)));
	}

}
