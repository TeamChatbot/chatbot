package de.morpheus.chatbot.utility;


public class NumberUtility {

	public static int random(int border){
		return (int) Math.floor((int)((Math.random()) * border + 1))-1;
	}
	
}
