package com.growthbeat.message.view;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView.ScaleType;

import com.growthbeat.message.model.Button;
import com.growthbeat.message.model.ImageButton;
import com.growthbeat.message.model.ImageMessage;

public class ImageMessageFragment extends Fragment {

	private ImageMessage imageMessage = null;
	private int loaderId = -1;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		Object message = getArguments().get("message");
		if (message == null)
			return null;
		if (!(message instanceof ImageMessage))
			return null;

		this.imageMessage = (ImageMessage) message;

		DisplayMetrics displayMetrics = getResources().getDisplayMetrics();

		double availableWidth = Math.min(imageMessage.getPicture().getWidth() * displayMetrics.density, displayMetrics.widthPixels * 0.75);
		double availableHeight = Math.min(imageMessage.getPicture().getHeight() * displayMetrics.density,
				displayMetrics.heightPixels * 0.75);
		double ratio = Math.min(availableWidth / imageMessage.getPicture().getWidth(), availableHeight
				/ imageMessage.getPicture().getHeight());

		int width = (int) (imageMessage.getPicture().getWidth() * ratio);
		int height = (int) (imageMessage.getPicture().getHeight() * ratio);
		int left = (int) ((displayMetrics.widthPixels - width) / 2);
		int top = (int) ((displayMetrics.heightPixels - height) / 2);

		FrameLayout baseLayout = new FrameLayout(getActivity());
		baseLayout.setBackgroundColor(Color.argb(128, 0, 0, 0));

		showImage(baseLayout, left, top, width, height, ratio);
		showImageButtons(baseLayout, left, top, width, height, ratio);

		return baseLayout;

	}

	private void showImage(FrameLayout baseLayout, int left, int top, int width, int height, double ratio) {

		FrameLayout frameLayout = new FrameLayout(getActivity());
		FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(width, height);
		layoutParams.setMargins(left, top, 0, 0);
		frameLayout.setLayoutParams(layoutParams);
		baseLayout.addView(frameLayout);

		ImageView imageView = new ImageView(getActivity(), imageMessage.getPicture().getUrl(), ratio);
		imageView.setScaleType(ScaleType.FIT_CENTER);
		imageView.setLayoutParams(new ViewGroup.LayoutParams(width, height));
		frameLayout.addView(imageView);

		getActivity().getSupportLoaderManager().initLoader(loaderId++, null, imageView);

	}

	private void showImageButtons(FrameLayout baseLayout, int left, int top, int width, int height, double ratio) {

		List<Button> buttons = extractButtons(Button.Type.image);

		int imageViewTop = top + height;
		for (Button button : buttons) {

			ImageButton imageButton = (ImageButton) button;

			int imageViewWidth = (int) (imageButton.getPicture().getWidth() * ratio);
			int imageViewHeight = (int) (imageButton.getPicture().getHeight() * ratio);
			int imageViewLeft = left + (width - imageViewWidth) / 2;
			imageViewTop -= imageViewHeight;

			FrameLayout frameLayout = new FrameLayout(getActivity());
			FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(imageViewWidth, imageViewHeight);
			layoutParams.setMargins(imageViewLeft, imageViewTop, 0, 0);
			frameLayout.setLayoutParams(layoutParams);
			baseLayout.addView(frameLayout);

			ImageView imageView = new ImageView(getActivity(), imageButton.getPicture().getUrl(), ratio);
			imageView.setScaleType(ScaleType.FIT_CENTER);
			imageView.setLayoutParams(new ViewGroup.LayoutParams(imageViewWidth, imageViewHeight));
			frameLayout.addView(imageView);

			getActivity().getSupportLoaderManager().initLoader(loaderId++, null, imageView);

		}

	}

	private List<Button> extractButtons(Button.Type type) {

		List<Button> buttons = new ArrayList<Button>();

		for (Button button : imageMessage.getButtons()) {
			if (button.getType() == type) {
				buttons.add(button);
			}
		}

		return buttons;

	}

}
