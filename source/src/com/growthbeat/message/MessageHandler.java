package com.growthbeat.message;

import com.growthbeat.message.model.GMMessage;

public interface MessageHandler {

	boolean handleMessage (GMMessage message, GrowthMessage manager);

}
