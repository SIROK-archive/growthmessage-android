package com.growthbeat.message;

import com.growthbeat.message.model.GMIntent;

public interface IntentHandler {

	boolean handleIntent(GMIntent intent);
	
}
