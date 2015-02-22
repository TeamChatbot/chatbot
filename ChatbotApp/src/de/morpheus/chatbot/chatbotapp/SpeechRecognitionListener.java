package de.morpheus.chatbot.chatbotapp;

import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.SpeechRecognizer;

import java.util.ArrayList;

public class SpeechRecognitionListener implements RecognitionListener  {

    private static SpeechRecognitionListener listenerInstance = null;

    SpeechControl listener;

    public static SpeechRecognitionListener getListenerInstance() {

        if(listenerInstance == null)
            listenerInstance = new SpeechRecognitionListener();

        return listenerInstance;
    }

    private SpeechRecognitionListener() { }

    public void setListener(SpeechControl listener) {

        this.listener = listener;
    }

    public void processSpeechCommands(ArrayList<String> recognitionResult) {

        listener.processSpeechCommands(recognitionResult);
    }

    public void onResults(Bundle data) {

        ArrayList<String> recognitionResult = data.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
        processSpeechCommands(recognitionResult);
    }

    public void onBeginningOfSpeech() {

        System.out.println("Starting to listen");
    }

    public void onBufferReceived(byte[] buffer) {

    }

    public void onEndOfSpeech() {

        System.out.println("Waiting for result...");
    }

    public void onError(int error) {

        if (listener != null)
            listener.restartSpeechRecognitionListener();
    }

    public void onEvent(int eventType, Bundle params) { }

    public void onPartialResults(Bundle partialResults) { }

    public void onReadyForSpeech(Bundle params) { }

    public void onRmsChanged(float rmsdB) { }
}