package com.growthbeat.message.handler;

import android.content.Context;
import android.content.Intent;

import com.growthbeat.message.model.Message;
import com.growthbeat.message.view.AlertActivity;

public class PlainMassageHandler implements MessageHandler {

	private Context context;

	public PlainMassageHandler(Context context) {
		this.context = context;
	}

	@Override
	public boolean handle(final Message message) {

		if (message.getType() != Message.Type.plain)
			return false;

		Intent intent = new Intent(context, AlertActivity.class);
		intent.putExtra("message", message);
		intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(intent);

		return true;

	}

}
