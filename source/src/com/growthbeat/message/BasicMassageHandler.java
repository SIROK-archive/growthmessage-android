package com.growthbeat.message;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.TextView;

import com.growthbeat.message.model.Message;
import com.growthbeat.message.model.PlainButton;
import com.growthbeat.message.model.PlainMessage;

public class BasicMassageHandler implements MessageHandler {
	Context context;

	public BasicMassageHandler(Context context) {
		this.context = context;
	}

	@Override
	public boolean handleMessage(final Message message, final GrowthMessage manager) {

		if (message.getType() != Message.Type.plain)
			return false;

		final PlainMessage plainMessage = (PlainMessage) message;

		ArrayList<String> labels = new ArrayList<String>();
		for (int i = 0; i < plainMessage.getButtons().size(); i++) {
			PlainButton plainButton = (PlainButton) plainMessage.getButtons().get(i);
			labels.add(plainButton.getLabel());
		}
		final CharSequence[] labelsArray = (CharSequence[]) labels.toArray(new CharSequence[0]);

		AlertDialog.Builder listDlg = new AlertDialog.Builder(context);

		TextView text = new TextView(context);
		text.setText(plainMessage.getText());
		listDlg.setView(text);

		listDlg.setTitle(plainMessage.getCaption());
		listDlg.setItems(labelsArray, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				manager.didSelectButton(plainMessage.getButtons().get(which), plainMessage);
			}
		});
		listDlg.create().show();
		return true;

	}

}
