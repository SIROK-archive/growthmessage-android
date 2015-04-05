package com.growthbeat.message.intenthandler;

import com.growthbeat.message.model.Intent;

public class NoopIntentHandler implements IntentHandler {

	@Override
	public boolean handleIntent(Intent intent) {
		return (intent.getType() == Intent.Type.noop);
	}

}
