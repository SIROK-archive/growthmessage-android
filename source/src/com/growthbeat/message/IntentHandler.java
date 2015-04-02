package com.growthbeat.message;

import com.growthbeat.message.model.Intent;

public interface IntentHandler {

	boolean handleIntent(Intent intent);
	
}
