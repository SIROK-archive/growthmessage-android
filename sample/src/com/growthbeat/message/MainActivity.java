package com.growthbeat.message;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;

import com.growthbeat.message.intenthandler.IntentHandler;
import com.growthbeat.message.intenthandler.NoopIntentHandler;
import com.growthbeat.message.intenthandler.UrlIntentHandler;
import com.growthbeat.message.model.Message;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		GrowthMessage.getInstance().initialize(getApplicationContext(), "P5C3vzoLOEijnlVj", "btFlFAitBJ1CBdL3IR3ROnhLYbeqmLlY");
		GrowthMessage.getInstance().getHttpClient().setBaseUrl("http://api.stg.message.growthbeat.com/");

		List<IntentHandler> intentHandlers = new ArrayList<IntentHandler>();
		intentHandlers.add(new UrlIntentHandler(getApplicationContext()));
		intentHandlers.add(new NoopIntentHandler());
		GrowthMessage.getInstance().setIntentHandlers(intentHandlers);

		GrowthMessage.getInstance().setCallback(new GrowthMessage.Callback() {

			@Override
			public boolean shouldShowMessage(Message message) {
				return true;
			}
		});
	}

	@Override
	public void onStart() {
		super.onStart();
		GrowthMessage.getInstance().recevieMessage("Event:P5C3vzoLOEijnlVj:Default:Open");
	}

	@Override
	public void onStop() {
		super.onStop();
	}

}
