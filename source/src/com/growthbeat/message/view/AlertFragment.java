package com.growthbeat.message.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.TextView;

import com.growthbeat.message.GrowthMessage;
import com.growthbeat.message.model.PlainButton;
import com.growthbeat.message.model.PlainMessage;

public class AlertFragment extends DialogFragment {

	public AlertFragment() {
		super();
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {

		Object message = getArguments().get("message");
		if (!(message instanceof PlainMessage))
			return null;

		final PlainMessage plainMessage = (PlainMessage) message;

		AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());

		dialogBuilder.setTitle(plainMessage.getCaption());

		TextView textView = new TextView(getActivity());
		textView.setText(plainMessage.getText());
		dialogBuilder.setView(textView);

		String[] labels = new String[plainMessage.getButtons().size()];
		for (int i = 0; i < plainMessage.getButtons().size(); i++) {
			PlainButton plainButton = (PlainButton) plainMessage.getButtons().get(i);
			labels[i] = plainButton.getLabel();
		}
		dialogBuilder.setItems(labels, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				GrowthMessage.getInstance().didSelectButton(plainMessage.getButtons().get(which), plainMessage);
			}
		});

		AlertDialog dialog = dialogBuilder.create();
		dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		dialog.setCanceledOnTouchOutside(false);

		return dialog;

	}

}
