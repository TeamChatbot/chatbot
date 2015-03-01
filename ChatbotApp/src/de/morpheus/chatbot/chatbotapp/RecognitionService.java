package de.morpheus.chatbot.chatbotapp;

import java.util.ArrayList;
import java.util.Locale;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.IBinder;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.util.Log;


public class RecognitionService extends Service implements RecognitionListener{

	private String LOG_TAG = "SpeechRecognitionActivity";
	private AudioManager audioManager;
	private SpeechRecognizer speechRecognizer = null;
	private Intent speechRecognizerIntent;
	private WebService webService;
    private String messageFromChatbot;
	private TextToSpeech textToSpeech;
	
	public void onCreate() {
		
		audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE); 
		
		speechRecognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
		speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
	    speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.GERMANY);
		
	    speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
		speechRecognizer.setRecognitionListener(this);
		
		webService = new WebService();
		
		textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            public void onInit(int status) {
                if(status!=TextToSpeech.ERROR)
                	textToSpeech.setLanguage(Locale.GERMANY);
            }
        });
	}
	
	public int onStartCommand(Intent intent, int flags, int startId) {
		
		audioManager.setStreamMute(AudioManager.STREAM_MUSIC, true);
		speechRecognizer.startListening(speechRecognizerIntent);
		
		return START_STICKY;
	}

	public IBinder onBind(Intent intent) {
		return null;
	}

	public void onReadyForSpeech(Bundle params) {
		Log.i(LOG_TAG, "onReadyForSpeech");
	}

	public void onBeginningOfSpeech() {
		Log.i(LOG_TAG, "onBeginningOfSpeech");
		MainActivity.speechInputLevel.setMax(10);
	}

	public void onRmsChanged(float rmsdB) {
		Log.i(LOG_TAG, "onRmsChanged: " + rmsdB);
		MainActivity.speechInputLevel.setProgress((int) rmsdB);
	}

	public void onBufferReceived(byte[] buffer) {
		Log.i(LOG_TAG, "onBufferReceived: " + buffer);
	}

	public void onEndOfSpeech() {
		Log.i(LOG_TAG, "onEndOfSpeech");
	}

	public void onError(int error) {
		
		String errorMessage = ErrorDescription.getErrorText(error);
		
		Log.d(LOG_TAG, "FAILED " + errorMessage);
		MainActivity.speechOutput.setText(errorMessage);
		
		if(error != 5) {
			speechRecognizer.startListening(speechRecognizerIntent);
		}
	}

	@SuppressWarnings("deprecation")
	public void onResults(Bundle results) {
		
		Log.i(LOG_TAG, "onResults");
		ArrayList<String> text = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
		final String speech = text.get(0);

		audioManager.setStreamMute(AudioManager.STREAM_MUSIC, false);

		messageFromChatbot = webService.com(speech);
		MainActivity.speechOutput.setText(speech);
		MainActivity.chatbotAnswer.setText(messageFromChatbot);
		
        if(textToSpeech!=null) {
            if(messageFromChatbot!=null) {
                if (!textToSpeech.isSpeaking()) {
                	CharSequence toSpeak = messageFromChatbot;
                    textToSpeech.speak(toSpeak.toString(), TextToSpeech.QUEUE_FLUSH, null);
                }
            }
            else {
            	MainActivity.chatbotAnswer.setText("Request to Chatbot WebService failed");
            }
        }
        
        while(textToSpeech.isSpeaking()) {
        	
        }
		
        audioManager.setStreamMute(AudioManager.STREAM_MUSIC, true);
        speechRecognizer.startListening(speechRecognizerIntent);
	}

	public void onPartialResults(Bundle partialResults) {
		Log.i(LOG_TAG, "onPartialResults");
	}

	public void onEvent(int eventType, Bundle params) {
		Log.i(LOG_TAG, "onEvent");
	}
	
	public void onDestroy(){ 
		
		if(textToSpeech!=null){
			textToSpeech.stop();
			textToSpeech.shutdown();
		}
		super.onDestroy();
	}
}