package com.growthbeat.message;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.os.Bundle;

public class MainActivity extends Activity {

	private SharedPreferences sharedPreferences = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		GrowthMessage.getInstance().initialize(MainActivity.this, "P5C3vzoLOEijnlVj", "btFlFAitBJ1CBdL3IR3ROnhLYbeqmLlY");
		GrowthMessage.getInstance().getHttpClient().setBaseUrl("http://api.stg.message.growthbeat.com/");

		AssetManager assetManager = getResources().getAssets();
		InputStream is;
		try {
		  is = assetManager.open("message_sample.json");
		  String sample = inputStreemToString(is);
		  GrowthMessage.getInstance().setSampleJSON(sample);
		} catch (Exception e) {
		  e.printStackTrace();
		}

	}

	public static String inputStreemToString(InputStream in) throws IOException{
        
	    BufferedReader reader = 
	        new BufferedReader(new InputStreamReader(in, "UTF-8"/* 文字コード指定 */));
	    StringBuffer buf = new StringBuffer();
	    String str;
	    while ((str = reader.readLine()) != null) {
	            buf.append(str);
	            buf.append("\n");
	    }
	    return buf.toString();
	}
	@Override
	public void onStart() {
		super.onStart();
		GrowthMessage.getInstance().openMessageIfAvailable();
	}

	@Override
	public void onStop() {
		super.onStop();
	}

}
