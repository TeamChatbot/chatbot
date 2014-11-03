package de.morpheus.chatbot.processing;

/**
 * Test unit to log messages the user puts in.
 * @author Blizzard Postapex
 *
 */
public class TextLogger 
implements de.morpheus.chatbot.processing.abstracts.InputProcessor<String, String> {
	
	private de.morpheus.chatbot.aiml.io.DAOInterface<String> writer = 
			new de.morpheus.chatbot.aiml.io.String_to_CSV_Writer();
	
	java.util.Random rand = new java.util.Random();

	@Override
	public String processInput(String input) {
		String message = "";
		try {
			writer.store(input);
			message = String.format("Successfully stored '%s' in %s%n"
					+ "Also, have a randomly selected already recorded message:%n%s",
					input, writer.toString(),
					writer.restoreByID(rand.nextInt(writer.getStoredCount()))
					);
		} catch (Exception e) {
			message = "Error occured during attempt to write '" + input + "' to " 
						+ writer.toString() + "\n" + e.getMessage();
			e.printStackTrace();
		}
		
		return message;
	}

}
