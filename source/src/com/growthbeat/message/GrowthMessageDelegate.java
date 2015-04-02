package com.growthbeat.message;

import com.growthbeat.message.model.Message;

public interface GrowthMessageDelegate {

	boolean shouldShowMessage(Message message);
	
}
