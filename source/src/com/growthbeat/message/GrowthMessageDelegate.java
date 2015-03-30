package com.growthbeat.message;

import com.growthbeat.message.model.GMMessage;

public interface GrowthMessageDelegate {

	boolean shouldShowMessage(GMMessage message);
	
}
