package de.morpheus.chatbot.chatbotapp;

import java.util.ArrayList;

public interface SpeechControl {

    public abstract void processSpeechCommands(ArrayList<String> speechCommands);

    public void restartSpeechRecognitionListener();
}
