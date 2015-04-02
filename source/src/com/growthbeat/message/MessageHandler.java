package com.growthbeat.message;

import com.growthbeat.message.model.Message;

public interface MessageHandler {

	boolean handleMessage (Message message, GrowthMessage manager);

}
