package de.morpheus.chatbot.chatbotapp;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;


public class MainActivity extends ListeningActivity {

	private RelativeLayout content;
	private TextView output;
	
	protected void onCreate(Bundle savedInstanceState) {
		
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        content = (RelativeLayout)findViewById(R.id.content);
        output = (TextView)findViewById(R.id.commands);
        context = getApplicationContext();
        
        SpeechRecognitionListener.getListenerInstance().setListener(this);
        startListening();
	}
	
	public void processSpeechCommands(ArrayList<String> recognitionResult) {
		
        content.removeAllViews();

        for(String s : recognitionResult)
            output.setText(s);
	}

	public void restartSpeechRecognitionListener() {
		
		
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
