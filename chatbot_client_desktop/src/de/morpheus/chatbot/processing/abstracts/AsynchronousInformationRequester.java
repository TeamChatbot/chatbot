package de.morpheus.chatbot.processing.abstracts;


/**
 * This class is designed to use the functionality of classes implementing the {@link InputProcessor} 
 * asynchronously. A class that wants to request processing of {@link InputProcessor} either 
 * subclasses the {@link SynchronousInformationRequester} for synchronous, or subclasses this class for 
 * asynchronous processing.
 * @author Blizzard Postapex
 *
 * @param <InputType> Self-explanatory
 * @param <OutputType> Self-explanatory
 */

public abstract class AsynchronousInformationRequester<InputType, OutputType>
extends InformationRequester<InputType, OutputType>{
	
	public AsynchronousInformationRequester(InputProcessor<InputType, OutputType> nextProcessor) {
		super(nextProcessor);
	}
	
	@Override
	public void requestInputProcessing(final InputType input) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				OutputType output = nextProcessor.processInput(input);
				synchronized(this) {
					onProcessFinished(output);
				}
			}}).start();
	}
}
