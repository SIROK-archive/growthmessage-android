package com.growthbeat.message.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

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
		if (message == null)
			return null;
		if (!(message instanceof PlainMessage))
			return null;

		final PlainMessage plainMessage = (PlainMessage) message;

		AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());

		dialogBuilder.setTitle(plainMessage.getCaption());
		dialogBuilder.setMessage(plainMessage.getText());

		if (plainMessage.getButtons() != null && plainMessage.getButtons().size() >= 2) {

			final PlainButton positiveButton = (PlainButton) plainMessage.getButtons().get(0);
			dialogBuilder.setPositiveButton(positiveButton.getLabel(), new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					GrowthMessage.getInstance().selectButton(positiveButton, plainMessage);
					if (!getActivity().isFinishing())
						getActivity().finish();
				}
			});

			final PlainButton negativeButton = (PlainButton) plainMessage.getButtons().get(1);
			dialogBuilder.setNegativeButton(negativeButton.getLabel(), new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					GrowthMessage.getInstance().selectButton(negativeButton, plainMessage);
					if (!getActivity().isFinishing())
						getActivity().finish();
				}
			});

		}

		AlertDialog dialog = dialogBuilder.create();
		dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		dialog.setCanceledOnTouchOutside(false);

		return dialog;

	}
}
