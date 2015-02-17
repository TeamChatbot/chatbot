package de.morpheus.chatbot.service;

import java.io.IOException;
import java.rmi.RemoteException;

import de.morpheus.chatbot.service.ChatbotServiceProxy;

public class Client {
	
	  public static void main( String[] args ) throws RemoteException
	  {
		  ChatbotServiceProxy service = new ChatbotServiceProxy();
		  service.setEndpoint("http://194.95.221.229:8080/chatbot_service_tomcat/services/ChatbotService");

	      byte buffer[] = new byte[80];
	      String input = "";
	      int read;
	      
	      do {
	        try {
	          read = System.in.read(buffer, 0, 80);
	          input = new String(buffer, 0, read);
	        }
	        catch(IOException e) {
	          e.printStackTrace();
	        }
	        
	        System.out.println(service.communicate(input));
	        
	      } while(! input.equals("exit"+System.getProperty("line.separator")));
	  }
}