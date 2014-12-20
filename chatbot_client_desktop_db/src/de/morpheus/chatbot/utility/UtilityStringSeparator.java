package de.morpheus.chatbot.utility;

import java.util.List;


public class UtilityStringSeparator {

	public static String separate(char separator, List<String> values){
		StringBuffer output = new StringBuffer();
		for(String value : values){
			output.append("" + value + "" + separator);
		}
		output.deleteCharAt(output.length()-1);
		return output.toString();
	}
	
	
}
