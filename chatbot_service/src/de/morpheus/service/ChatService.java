package de.morpheus.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import de.morpheus.chatbot.base.Chatbot;

@Path("/chat")
public class ChatService {
	
	private Chatbot chatbot = new Chatbot();
	
	@GET
	@Path("alive")
	@Produces("text/plain")
	public String alive(){
		System.err.println("I AM ALIVE");
		return "... i am alive :)";
	}

	
	@GET
	@Path("message") 
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.TEXT_PLAIN)
	public String message(@QueryParam(value="message") String message){
		System.out.println("message received ...");
		return this.chatbot.processInput(message);
	}
	
	@GET
	@Path("test")
	@Produces("text/plain")
	public String test(){
		return "helloo";
	}
}
