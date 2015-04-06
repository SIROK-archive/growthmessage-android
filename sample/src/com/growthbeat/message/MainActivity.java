package com.growthbeat.message;

import android.app.Activity;
import android.os.Bundle;

import com.growthbeat.message.model.Message;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		GrowthMessage.getInstance().initialize(getApplicationContext(), "P5C3vzoLOEijnlVj", "btFlFAitBJ1CBdL3IR3ROnhLYbeqmLlY");
		GrowthMessage.getInstance().getHttpClient().setBaseUrl("http://api.stg.message.growthbeat.com/");

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
