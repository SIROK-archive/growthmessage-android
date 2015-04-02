package com.growthbeat.message;

import com.growthbeat.message.model.Intent;

public class NopeIntentHandler implements IntentHandler {

	@Override
	public boolean handleIntent(Intent intent) {
		if (intent.getType().equals("noop"))
			return true;
		else 
			return false;
	}

}
