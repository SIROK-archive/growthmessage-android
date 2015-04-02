package com.growthbeat.message;

import java.util.ArrayList;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.growthbeat.message.model.GMMessage;

public class MainActivity extends Activity {

	private SharedPreferences sharedPreferences = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		GrowthMessage.getInstance().initialize(getApplicationContext(), "P5C3vzoLOEijnlVj", "btFlFAitBJ1CBdL3IR3ROnhLYbeqmLlY");
		GrowthMessage.getInstance().getHttpClient().setBaseUrl("http://api.stg.message.growthbeat.com/");

		ArrayList<MessageHandler> messageHandlers;
		messageHandlers = new ArrayList<MessageHandler>();
		messageHandlers.add(new BasicMassageHandler(MainActivity.this));
		GrowthMessage.getInstance().setMessageHandlers(messageHandlers);

		ArrayList<IntentHandler> intentHandlers;
		intentHandlers = new ArrayList<IntentHandler>();
		intentHandlers.add(new OpenBrowserIntentHandler(MainActivity.this));
		intentHandlers.add(new NopeIntentHandler());
		GrowthMessage.getInstance().setIntentHandlers(intentHandlers);

		GrowthMessageDelegate delegate = new GrowthMessageDelegate() {
			@Override
			public boolean shouldShowMessage(GMMessage message) {
				return true;
			}
		};
		GrowthMessage.getInstance().setDelegate(delegate);
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
