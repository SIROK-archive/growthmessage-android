package com.growthbeat.message.handler;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.TextView;

import com.growthbeat.message.GrowthMessage;
import com.growthbeat.message.model.Message;
import com.growthbeat.message.model.PlainButton;
import com.growthbeat.message.model.PlainMessage;

public class PlainMassageHandler implements MessageHandler {

	Context context;

	public PlainMassageHandler(Context context) {
		this.context = context;
	}

	@Override
	public boolean handle(final Message message) {

		if (message.getType() != Message.Type.plain)
			return false;

		final PlainMessage plainMessage = (PlainMessage) message;

		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

		alertDialogBuilder.setTitle(plainMessage.getCaption());

		TextView textView = new TextView(context);
		textView.setText(plainMessage.getText());
		alertDialogBuilder.setView(textView);

		String[] labels = new String[plainMessage.getButtons().size()];
		for (int i = 0; i < plainMessage.getButtons().size(); i++) {
			PlainButton plainButton = (PlainButton) plainMessage.getButtons().get(i);
			labels[i] = plainButton.getLabel();
		}
		alertDialogBuilder.setItems(labels, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				GrowthMessage.getInstance().didSelectButton(plainMessage.getButtons().get(which), message);
			}
		});

		AlertDialog alertDialog = alertDialogBuilder.create();
		alertDialog.show();

		return true;

	}

}
