package com.growthbeat.message.handler;

import com.growthbeat.message.GrowthMessage;
import com.growthbeat.message.model.Message;

public interface MessageHandler {

	boolean handleMessage (Message message, GrowthMessage manager);

}
