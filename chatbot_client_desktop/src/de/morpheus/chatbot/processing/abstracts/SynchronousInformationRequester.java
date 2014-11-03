package de.morpheus.chatbot.processing.abstracts;


/**
 * This class is designed to use the functionality of classes implementing the {@link InputProcessor} 
 * synchronously. A class that wants to request processing of {@link InputProcessor} either 
 * subclasses the {@link AsynchronousInformationRequester} for asynchronous, or subclasses this class for 
 * synchronous processing.
 * @author Blizzard Postapex
 *
 * @param <InputType> Self-explanatory
 * @param <OutputType> Self-explanatory
 */

public abstract class SynchronousInformationRequester<InputType, OutputType> 
extends InformationRequester<InputType, OutputType>{
	
	public SynchronousInformationRequester(InputProcessor<InputType, OutputType> nextProcessor) {
		super(nextProcessor);
	}
	
	@Override
	public void requestInputProcessing(final InputType input) {
		OutputType output = nextProcessor.processInput(input);
		onProcessFinished(output);
	}
}
