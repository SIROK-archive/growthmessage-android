package com.growthbeat.message.handler;

import android.content.Context;

import com.growthbeat.message.model.Message;

public class ImageMassageHandler implements MessageHandler {

	private Context context;

	public ImageMassageHandler(Context context) {
		this.context = context;
	}

	@Override
	public boolean handle(final Message message) {

		if (message.getType() != Message.Type.image)
			return false;

		// TODO show message

		return true;

	}

}
