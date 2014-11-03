package de.morpheus.chatbot.processing.abstracts;

/**
 * Accepts input and produces output. What else is there to say?
 */
public interface InputProcessor<InputType, OutputType> {
	/**
	 * @see InputProcessor
	 */
	OutputType processInput(InputType input);
}
