package de.morpheus.chatbot.chatbotapp;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.widget.Toast;

import java.util.Locale;
import java.util.ArrayList;

public abstract class ListeningActivity extends Activity implements SpeechControl {

    protected SpeechRecognizer speechrecognizer;
    protected Context context;

    protected void startListening() {

        try {

            initSpeech();
            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.GERMANY);

            if (!intent.hasExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE))
                intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, "com.dummy");

            speechrecognizer.startListening(intent);
        }

        catch (ActivityNotFoundException exception) {

            Toast.makeText(getApplicationContext(), "Error! Your device is not supported", Toast.LENGTH_SHORT).show();
        }
    }

    protected void stopListening() {

        if (speechrecognizer != null) {
            speechrecognizer.stopListening();
            speechrecognizer.cancel();
            speechrecognizer.destroy();
        }
        speechrecognizer = null;
    }

    protected void initSpeech() {

        if (speechrecognizer == null) {
            speechrecognizer = SpeechRecognizer.createSpeechRecognizer(this);
            if (!SpeechRecognizer.isRecognitionAvailable(context)) {
                Toast.makeText(context, "Speech Recognition is not available",
                        Toast.LENGTH_LONG).show();
                finish();
            }
            speechrecognizer.setRecognitionListener(SpeechRecognitionListener.getListenerInstance());
        }
    }

    public void finish() {

        stopListening();
        super.finish();
    }

    protected void onStop() {

        stopListening();
        super.onStop();
    }

    protected void onDestroy() {

        if (speechrecognizer != null) {
            speechrecognizer.stopListening();
            speechrecognizer.cancel();
            speechrecognizer.destroy();
        }
        super.onDestroy();
    }

    protected void onPause() {

        if(speechrecognizer!=null){
            speechrecognizer.stopListening();
            speechrecognizer.cancel();
            speechrecognizer.destroy();

        }
        speechrecognizer = null;

        super.onPause();
    }

    public void restartListeningService() {

        stopListening();
        startListening();
    }
}
