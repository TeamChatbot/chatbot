package de.morpheus.chatbot.processing;

import de.morpheus.chatbot.processing.abstracts.InputProcessor;


/**
 * Just parrots everything the user says, however with delay. 
 * Nothing you want to use as the final result.
 * @author Blizzard Postapex
 *
 */
public class DummyProcessor implements InputProcessor<String, String> {

	@Override
	public String processInput(String input) {
		for(int i = 0; i < Integer.MAX_VALUE; i++);
		return "You just said: " + input;
	}

}
