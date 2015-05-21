package com.growthbeat.message.view;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView.ScaleType;
import android.widget.ProgressBar;

import com.growthbeat.message.GrowthMessage;
import com.growthbeat.message.model.Button;
import com.growthbeat.message.model.CloseButton;
import com.growthbeat.message.model.ImageButton;
import com.growthbeat.message.model.ImageMessage;
import com.growthbeat.message.model.ScreenButton;

public class ImageMessageFragment extends Fragment {

	private FrameLayout baseLayout = null;
	private FrameLayout innerLayout = null;
	private ImageMessage imageMessage = null;
	
	private ProgressBar circle = null;
	private int loaderId = -1;
	private int imageCount = 0;
	
	private Runnable successRun = new Runnable() {
		@Override
		public void run() {
			imageCount--;
			if (imageCount < 0) {
				innerLayout.setVisibility(View.VISIBLE);
				circle.setVisibility(View.GONE);
			}
		}
	};
	
	private Runnable failureRun = new Runnable() {
		@Override
		public void run() {
			getActivity().finish();
		}
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		Object message = getArguments().get("message");
		if (message == null || !(message instanceof ImageMessage))
			return null;

		this.imageMessage = (ImageMessage) message;
		this.imageCount = imageMessage.getButtons().size();

		DisplayMetrics displayMetrics = getResources().getDisplayMetrics();

		double displayDensity = displayMetrics.density, displayWidth = displayMetrics.widthPixels, displayHeight = displayMetrics.heightPixels;
		double availableWidth = Math.min(imageMessage.getPicture().getWidth() * displayDensity, displayWidth * 0.75);
		double availableHeight = Math.min(imageMessage.getPicture().getHeight() * displayDensity, displayHeight * 0.75);
		double ratio = Math.min(availableWidth / imageMessage.getPicture().getWidth(), availableHeight / imageMessage.getPicture().getHeight());

		int width = (int) (imageMessage.getPicture().getWidth() * ratio);
		int height = (int) (imageMessage.getPicture().getHeight() * ratio);
		int left = (int) ((displayWidth - width) / 2);
		int top = (int) ((displayHeight - height) / 2);

		Rect rect = new Rect(left, top, width, height);

		baseLayout = new FrameLayout(getActivity());
		baseLayout.setBackgroundColor(Color.argb(128, 0, 0, 0));
		
		innerLayout = new FrameLayout(getActivity());
		innerLayout.setVisibility(View.GONE);

		showLoadDialog(baseLayout, ratio);
		showImage(innerLayout, rect);
		showScreenButton(innerLayout, rect);
		showImageButtons(innerLayout, rect, ratio);
		showCloseButton(innerLayout, rect, ratio);
		
		baseLayout.addView(innerLayout);

		return baseLayout;

	}
	
	private void showLoadDialog(FrameLayout baseLayout, double ratio) {
        circle = new ProgressBar(getActivity(), null, android.R.attr.progressBarStyleLarge);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams((int) (100 * ratio), (int) (100 * ratio));
        layoutParams.gravity = Gravity.CENTER;
        
        baseLayout.addView(circle, layoutParams);
	}

	private void showImage(FrameLayout innerLayout, Rect rect) {

		UrlImageView urlImageView = new UrlImageView(getActivity(), imageMessage.getPicture().getUrl(), successRun, failureRun);
		urlImageView.setScaleType(ScaleType.FIT_CENTER);
		getActivity().getSupportLoaderManager().initLoader(loaderId++, null, urlImageView);

		innerLayout.addView(wrapViewWithAbsoluteLayout(urlImageView, rect));

	}

	private void showScreenButton(FrameLayout innerLayout, Rect rect) {

		List<Button> buttons = extractButtons(Button.Type.screen);

		if (buttons.size() < 1)
			return;

		final ScreenButton screenButton = (ScreenButton) buttons.get(0);

		UrlImageButton urlImageButton = new UrlImageButton(getActivity(), imageMessage.getPicture().getUrl(), successRun, failureRun);
		urlImageButton.setScaleType(ScaleType.FIT_CENTER);
		urlImageButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				GrowthMessage.getInstance().selectButton(screenButton, imageMessage);
				if (!getActivity().isFinishing())
					getActivity().finish();
			}
		});
		getActivity().getSupportLoaderManager().initLoader(loaderId++, null, urlImageButton);

		innerLayout.addView(wrapViewWithAbsoluteLayout(urlImageButton, rect));

	}

	private void showImageButtons(FrameLayout innerLayout, Rect rect, double ratio) {

		List<Button> buttons = extractButtons(Button.Type.image);
		Collections.reverse(buttons);

		int top = rect.getTop() + rect.getHeight();
		for (Button button : buttons) {

			final ImageButton imageButton = (ImageButton) button;

			int width = (int) (imageButton.getPicture().getWidth() * ratio);
			int height = (int) (imageButton.getPicture().getHeight() * ratio);
			int left = rect.getLeft() + (rect.getWidth() - width) / 2;
			top -= height;

			UrlImageButton urlImageButton = new UrlImageButton(getActivity(), imageButton.getPicture().getUrl(), successRun, failureRun);
			urlImageButton.setScaleType(ScaleType.FIT_CENTER);
			urlImageButton.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					GrowthMessage.getInstance().selectButton(imageButton, imageMessage);
					if (!getActivity().isFinishing())
						getActivity().finish();
				}
			});
			getActivity().getSupportLoaderManager().initLoader(loaderId++, null, urlImageButton);

			innerLayout.addView(wrapViewWithAbsoluteLayout(urlImageButton, new Rect(left, top, width, height)));

		}

	}

	private void showCloseButton(FrameLayout innerLayout, Rect rect, double ratio) {

		List<Button> buttons = extractButtons(Button.Type.close);

		if (buttons.size() < 1)
			return;

		final CloseButton closeButton = (CloseButton) buttons.get(0);

		int width = (int) (closeButton.getPicture().getWidth() * ratio);
		int height = (int) (closeButton.getPicture().getHeight() * ratio);
		int left = rect.getLeft() + rect.getWidth() - width / 2;
		int top = rect.getTop() - height / 2;

		UrlImageButton urlImageButton = new UrlImageButton(getActivity(), closeButton.getPicture().getUrl(), successRun, failureRun);
		urlImageButton.setScaleType(ScaleType.FIT_CENTER);
		urlImageButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				GrowthMessage.getInstance().selectButton(closeButton, imageMessage);
				if (!getActivity().isFinishing())
					getActivity().finish();
			}
		});
		getActivity().getSupportLoaderManager().initLoader(loaderId++, null, urlImageButton);

		innerLayout.addView(wrapViewWithAbsoluteLayout(urlImageButton, new Rect(left, top, width, height)));

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

	private View wrapViewWithAbsoluteLayout(View view, Rect rect) {

		FrameLayout frameLayout = new FrameLayout(getActivity());
		FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(rect.getWidth(), rect.getHeight());
		layoutParams.setMargins(rect.getLeft(), rect.getTop(), 0, 0);
		frameLayout.setLayoutParams(layoutParams);

		view.setLayoutParams(new ViewGroup.LayoutParams(rect.getWidth(), rect.getHeight()));
		frameLayout.addView(view);

		return frameLayout;

	}

}
