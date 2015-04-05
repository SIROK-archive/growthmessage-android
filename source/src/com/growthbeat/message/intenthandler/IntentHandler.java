package com.growthbeat.message.intenthandler;

import com.growthbeat.message.model.Intent;

public interface IntentHandler {

	boolean handle(Intent intent);
	
}
