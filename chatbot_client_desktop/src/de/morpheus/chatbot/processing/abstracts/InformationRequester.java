package de.morpheus.chatbot.processing.abstracts;


/**
 * A wrapper for the synchronous and asynchronous information requester, 
 * so that they can be used interchangibly.
 * @author Blizzard Postapex 
 *
 * @param <InputType> Self-explanatory
 * @param <OutputType> Self-explanatory
 */
public abstract class InformationRequester<InputType, OutputType> {
	
	protected final InputProcessor<InputType, OutputType> nextProcessor;
	
	protected InformationRequester(InputProcessor<InputType, OutputType> nextProcessor) {
		this.nextProcessor = nextProcessor;
	}

	/**
	 * Should be called when input processing has finished, and only then!
	 */
	protected abstract void onProcessFinished(OutputType result);
	
	/**
	 * Lets the specified {@link InputProcessor} process the input, duh.<br>
	 * Should also invoke the method {@link #onProcessFinished}, 
	 * once the processing is finished, another duh.
	 */
	public abstract void requestInputProcessing(InputType input);
}
