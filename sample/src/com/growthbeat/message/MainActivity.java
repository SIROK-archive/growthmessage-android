package com.growthbeat.message;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.growthbeat.analytics.GrowthAnalytics;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		GrowthMessage.getInstance().initialize(getApplicationContext(), "P5C3vzoLOEijnlVj", "btFlFAitBJ1CBdL3IR3ROnhLYbeqmLlY");
		GrowthMessage.getInstance().getHttpClient().setBaseUrl("http://api.stg.message.growthbeat.com/");

		findViewById(R.id.purchase_button).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				GrowthAnalytics.getInstance().purchase(100, "item", "energy");
			}
		});

	}

	@Override
	public void onStart() {
		super.onStart();
		GrowthAnalytics.getInstance().open();
	}

	@Override
	public void onStop() {
		super.onStop();
		GrowthAnalytics.getInstance().close();
	}

}
