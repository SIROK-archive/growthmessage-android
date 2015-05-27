package com.growthbeat.message.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import com.growthbeat.message.model.PlainMessage;

public class CustomPlainMessageFragment extends DialogFragment {

	public CustomPlainMessageFragment() {
		super();
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		try {
			super.onActivityCreated(savedInstanceState);
		} catch (Exception e) {
			getActivity().finish();
		}
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {

		Object message = getArguments().get("message");
		if ((message == null) || !(message instanceof PlainMessage))
			return null;

		final PlainMessage plainMessage = (PlainMessage) message;

		AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());

		dialogBuilder.setTitle(plainMessage.getCaption());
		dialogBuilder.setMessage(plainMessage.getText());

		if (plainMessage.getButtons().size() >= 3)
			return null;
		
		
//		LayoutInflater inflater = getLayoutInflater(savedInstanceState);
//		FrameLayout f1 = (FrameLayout)alert.findViewById(android.R.id.body);
//		f1.addView(inflater.inflate(R.layout.dialog_view, f1, false));

//		final PlainButton positiveButton;
//		final PlainButton negativeButton;
//
//		switch (plainMessage.getButtons().size()) {
//		case 1:
//			positiveButton = (PlainButton) plainMessage.getButtons().get(0);
//			negativeButton = null;
//			break;
//		case 2:
//			positiveButton = (PlainButton) plainMessage.getButtons().get(0);
//			negativeButton = (PlainButton) plainMessage.getButtons().get(1);
//			break;
//		default:
//			return null;
//		}
//
//		if (positiveButton != null) {
//			dialogBuilder.setPositiveButton(positiveButton.getLabel(), new DialogInterface.OnClickListener() {
//				@Override
//				public void onClick(DialogInterface dialog, int which) {
//					GrowthMessage.getInstance().selectButton(positiveButton, plainMessage);
//					if (!getActivity().isFinishing())
//						getActivity().finish();
//				}
//			});
//		}
//
//		if (negativeButton != null) {
//			dialogBuilder.setNegativeButton(negativeButton.getLabel(), new DialogInterface.OnClickListener() {
//				@Override
//				public void onClick(DialogInterface dialog, int which) {
//					GrowthMessage.getInstance().selectButton(negativeButton, plainMessage);
//					if (!getActivity().isFinishing())
//						getActivity().finish();
//				}
//			});
//		}

		AlertDialog dialog = dialogBuilder.create();
		dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		dialog.setCanceledOnTouchOutside(false);

		return dialog;

	}

}
