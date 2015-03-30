package com.growthbeat.message;

import com.growthbeat.message.model.GMIntent;

public class NopeIntentHandler implements IntentHandler {

	@Override
	public boolean handleIntent(GMIntent intent) {
		if (intent.getType().equals("noop"))
			return true;
		else 
			return false;
	}

}
