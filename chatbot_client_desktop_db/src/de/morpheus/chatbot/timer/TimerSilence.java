package de.morpheus.chatbot.timer;

import java.util.Timer;
import java.util.TimerTask;

import org.alicebot.ab.Chat;

public class TimerSilence extends Timer {
	
	public void start(final Chat chatSession){
		super.scheduleAtFixedRate(new TimerTask() {
			public void run() {
				System.out.println(chatSession.multisentenceRespond("<silence/>"));
			}
		}, 50000, 50000);
	}
	
}
