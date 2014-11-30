package de.morpheus.chatbot.mobile;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.Engine;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.support.v7.app.ActionBarActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chatbot_mobile.R;

public class TestChatActivity extends ActionBarActivity implements OnClickListener, OnInitListener, RecognitionListener {

	private static final int RESULT_SPEECH 	= 1;
 	private static final int TTS_DATA_CHECK = 2;
	
	private ImageButton 	imgbSttTrigger;
	private TextView 		txtvChatHistory;
	private ScrollView 		scrvChatHistory;
	private TextToSpeech 	tts;
	private SpeechRecognizer speechRecognizer;
	private Spannable wordToSpan;        
    private Handler handler = new Handler();
	
	Intent intent = null; 
	
	private Runnable runnable = new Runnable() {
		   @Override
		   public void run() {
			   /*Toast t = Toast.makeText(getApplicationContext(),"SilenceTimer aktiviert", Toast.LENGTH_SHORT);
	   		   t.show();*/
			   String response = TestChatActivity.this.prepareWebServiceCall("<silence/>");
			   TestChatActivity.this.communicate(null, response);		
			   handler.postDelayed(this, 30000);
		   }
	};
			
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	this.tts = new TextToSpeech(this,this);
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        this.imgbSttTrigger 	= (ImageButton)findViewById(R.id.imgb_stt_trigger);
        this.txtvChatHistory 	= (TextView)findViewById(R.id.txtv_chat_history);
        this.scrvChatHistory	= (ScrollView)findViewById(R.id.scrv_chat_history);
        this.imgbSttTrigger.setOnClickListener(this);
        this.checkIfTTSIsInstalled();
        
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);       
        speechRecognizer.setRecognitionListener(this);     
        
        intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);        
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "de-DE");
        intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,"voice.recognition.test");
        intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS,1);
        
        handler.postDelayed(runnable, 30000);
        //speechRecognizer.startListening(intent);
    }

    private void checkIfTTSIsInstalled() {
	    Intent intent = new Intent(Engine.ACTION_CHECK_TTS_DATA);
	    startActivityForResult(intent, TTS_DATA_CHECK);
	}

    
    public String prepareWebServiceCall(String message){
    	HttpURLConnection urlConnection = null;
  	   	try {
  	   		String returnValue = "";
  	   		message = URLEncoder.encode(message);
	   		InputStream in = null;
  	   		try{
  	   			//Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("78.47.182.10", 80));
	  	   		URL url = new URL("http://xsmorpheussx.ddns.net:8080/chatbot_service/ws/chat/message?message=" + message);
	  	   		//urlConnection = (HttpURLConnection) url.openConnection(proxy);
	  	   		urlConnection = (HttpURLConnection) url.openConnection();
	  	   		urlConnection.setConnectTimeout(30000);
	  	   		urlConnection.setReadTimeout(30000);
  	  	   		in = new BufferedInputStream(urlConnection.getInputStream());
  	   		}catch(Exception e){
  	   			Toast t = Toast.makeText(getApplicationContext(),"Keine Verbindung zum Server", Toast.LENGTH_SHORT);
  	   			t.show();
  	   		}
  	   		if(in != null){
	  	   		int character = -1;
	  	   		while((character = in.read()) != -1){
	  	   			returnValue += ((char)character);
	  	   		}
  	   		}
  	   		return returnValue;
  	   	} catch (IOException e) {
			e.printStackTrace();
		} finally {
  	   		urlConnection.disconnect();
  	   	}
  	   	return null;
    }
    
	@Override
	public void onClick(View v) {
		if(v.getId() == R.id.imgb_stt_trigger){
			this.recognizeSpeech();
		}
	}
	
	private void recognizeSpeech() {
		Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "de-DE");
        try {
            startActivityForResult(intent, RESULT_SPEECH);
        } catch (ActivityNotFoundException a) {
            Toast t = Toast.makeText(getApplicationContext(),"Dein Handy unterstützt kein STT", Toast.LENGTH_SHORT);
            t.show();
        }
	}

	@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
	        case RESULT_SPEECH: {
	            if (resultCode == RESULT_OK && null != data) {
	            	this.handler.removeCallbacks(runnable);
	                ArrayList<String> text = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
	                String responseOfBot= this.prepareWebServiceCall(text.get(0));
	                this.communicate(text.get(0), responseOfBot);
	                handler.postDelayed(runnable, 10000);
	            }
	            break;
	        }
	        case TTS_DATA_CHECK: {
	        	if (resultCode != Engine.CHECK_VOICE_DATA_PASS) {
	        		Intent installIntent = new Intent(Engine.ACTION_INSTALL_TTS_DATA);
	                startActivity(installIntent);
	        	}
	        	break;
	        }
        }
    }

	private void communicate(String userMessage, String botMessage){
		String userMessageText = (userMessage != null) ? "\nUser: " + userMessage : "";
		wordToSpan = new SpannableString(userMessageText);
		wordToSpan.setSpan(new ForegroundColorSpan(Color.WHITE), 0, wordToSpan.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		this.txtvChatHistory.append(wordToSpan);
		String botMessageText = (botMessage != null) ? "\nBot: " + botMessage : "";
		wordToSpan = new SpannableString(botMessageText);
		wordToSpan.setSpan(new ForegroundColorSpan(Color.YELLOW), 0, wordToSpan.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        this.txtvChatHistory.append(wordToSpan);
        this.scrvChatHistory.scrollTo(this.scrvChatHistory.getBottom(), this.scrvChatHistory.getBottom());
        tts.setLanguage(Locale.GERMAN);
        tts.speak(botMessage, TextToSpeech.QUEUE_FLUSH, null);
	}
	
	@Override
	public void onInit(int status) {
		
	}

	@Override
	public void onBeginningOfSpeech() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onBufferReceived(byte[] buffer) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onEndOfSpeech() {

	}

	@Override
	public void onError(int error) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onEvent(int eventType, Bundle params) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPartialResults(Bundle partialResults) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onReadyForSpeech(Bundle params) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void onResults(Bundle results) {
		 String message = new String();
         List<String> data = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
         for (int i = 0; i < data.size(); i++){
        	 message += data.get(i);
         }
         String responseOfBot= this.prepareWebServiceCall(message);
         this.communicate(message, responseOfBot);
	}

	@Override
	public void onRmsChanged(float rmsdB) {
		// TODO Auto-generated method stub
		
	}

}
