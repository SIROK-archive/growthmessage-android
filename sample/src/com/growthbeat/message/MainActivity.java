package com.growthbeat.message;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;

public class MainActivity extends Activity {

	private SharedPreferences sharedPreferences = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		GrowthMessage.getInstance().initialize(getApplicationContext(), "P5C3vzoLOEijnlVj", "btFlFAitBJ1CBdL3IR3ROnhLYbeqmLlY");
		GrowthMessage.getInstance().getHttpClient().setBaseUrl("http://api.stg.message.growthbeat.com/");

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
