package de.morpheus.chatbot.chatbotapp;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.ToggleButton;


public class MainActivity extends Activity {

	private Intent recognitionService = null;
	protected static ToggleButton turnRecognitionOnOff;
	protected static ProgressBar speechInputLevel;
	protected static TextView speechOutput;
	protected static TextView chatbotAnswer;

	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        
        turnRecognitionOnOff = (ToggleButton) findViewById(R.id.turnRecognitionOnOff);
		speechOutput = (TextView) findViewById(R.id.speechOutput);
		chatbotAnswer = (TextView) findViewById(R.id.chatbotAnswer);
		speechInputLevel = (ProgressBar) findViewById(R.id.speechInputLevel);
		speechInputLevel.setVisibility(View.VISIBLE);
		
		recognitionService = new Intent(this, RecognitionService.class);
		
		turnRecognitionOnOff.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			 
			 public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
			 
				 if (isChecked) {
					 startService(recognitionService);
				 } 
				 else {
					 speechInputLevel.setProgress(-2);
					 stopService(recognitionService);
				 }
			 }
		});
	}
	
	public void onResume() {
		super.onResume();	
	}
	
	protected void onPause() {
		super.onPause();
	}
	
    protected void onDestroy(){
        super.onDestroy();
    }
}