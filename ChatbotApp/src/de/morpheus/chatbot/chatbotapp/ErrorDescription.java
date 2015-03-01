package de.morpheus.chatbot.chatbotapp;

import android.speech.SpeechRecognizer;

public class ErrorDescription {

	private static String errorMessage;
	
	public static String getErrorText(int errorCode) {
		
		 switch (errorCode) {
		 
			 case SpeechRecognizer.ERROR_AUDIO:
				 errorMessage = "Audio recording error";
			 break;
			 
			 case SpeechRecognizer.ERROR_CLIENT:
				 errorMessage = "Client side error";
			 break;
			 
			 case SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS:
				 errorMessage = "Insufficient permissions";
			 break;
			 
			 case SpeechRecognizer.ERROR_NETWORK:
				 errorMessage = "Network error";
			 break;
			 
			 case SpeechRecognizer.ERROR_NETWORK_TIMEOUT:
				 errorMessage = "Network timeout";
			 break;
			 
			 case SpeechRecognizer.ERROR_NO_MATCH:
				 errorMessage = "No match";
			 break;
			 
			 case SpeechRecognizer.ERROR_RECOGNIZER_BUSY:
				 errorMessage = "RecognitionService busy";
			 break;
			 
			 case SpeechRecognizer.ERROR_SERVER:
				 errorMessage = "error from server";
			 break;
			 
			 case SpeechRecognizer.ERROR_SPEECH_TIMEOUT:
				 errorMessage = "No speech input";
			 break;
			 
			 default:
				 errorMessage = "Didn't understand, please try again.";
			 break;
		 }
	return errorMessage;
	}
}