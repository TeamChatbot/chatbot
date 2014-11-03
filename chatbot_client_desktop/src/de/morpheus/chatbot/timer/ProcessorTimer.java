package de.morpheus.chatbot.timer;

import java.util.Timer;
import java.util.TimerTask;

import de.morpheus.chatbot.processing.abstracts.InformationRequester;

/**
 * This class nudges the specified {@link InformationRequester} to request information, 
 * if it hasn't done so within a set amount of time. <br>
 * Preferrably, an instance of this class should be kept 
 * in the requester class. <br>
 * There is no return in this class, therefore it's unnecessary to specify an output type.
 * @author Blizzard Postapex
 *
 * @param <InputType>
 */
public class ProcessorTimer<InputType> extends Timer {
	
	private InformationRequester<InputType, ?> requester;
	private float timeout;
	private InputType defaultInput;
	
	private TimerTask task = null;
		
	private class RequestTask extends TimerTask {
		@Override
		public void run() {
			requester.requestInputProcessing(defaultInput);	
		}
	}
	
	/**
	 * @see ProcessorTimer
	 * @param requester OutputType is irrelevant because there's no return.
	 * @param timeout Naturally, greater than 0
	 * @param defaultInput What input during request.
	 */
	public ProcessorTimer(InformationRequester<InputType, ?> requester,
			float timeout, InputType defaultInput) {
		this.requester = requester;
		this.defaultInput = defaultInput;
		setTimeout(timeout);
		
		resetTimer();
	}
	
	public void resetTimer() {
		if(task != null) {
			task.cancel();
		}
		task = new RequestTask();
		scheduleAtFixedRate(task, (long) (timeout * 1000), (long) (timeout * 1000));
	}
	
	public float getTimeout() {
		return timeout;
	}
	
	public void setTimeout(final float timeout) throws IllegalArgumentException {
		if(timeout <= 0.f) throw new IllegalArgumentException("Timeout has to be greater than zero!");
		this.timeout = timeout;
		resetTimer();
	}
	
	@Override
	protected void finalize() {
		task.cancel();
		cancel();
	}
	
	/**
	 * Dummy Object that doesn't do a single thing.
	 */
	public static class NullTimer<T> extends ProcessorTimer<T> {

		public NullTimer() {
			super(null, 0.f, null);
		}
		
		@Override
		public void setTimeout(final float timeout) {}
		
		@Override
		public void resetTimer() {}
		
		@Override
		protected void finalize() {}
		
	}
}
