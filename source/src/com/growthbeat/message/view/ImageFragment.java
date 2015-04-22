package com.growthbeat.message.view;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.growthbeat.message.model.Button;
import com.growthbeat.message.model.CloseButton;
import com.growthbeat.message.model.ImageButton;
import com.growthbeat.message.model.ImageMessage;

public class ImageFragment extends Fragment {

	private ImageMessage imageMessage = null;
	private int loaderId = -1;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Object message = getArguments().get("message");
		if (message == null)
			return;

		if (!(message instanceof ImageMessage))
			return;

		this.imageMessage = (ImageMessage) message;

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		FrameLayout view = (FrameLayout) getActivity().findViewById(android.R.id.content);

		double availableWidth = Math.min(this.imageMessage.getPicture().getWidth(), view.getWidth() * 0.75), availableHeight = Math.min(
				this.imageMessage.getPicture().getHeight(), view.getHeight() * 0.75);
		double ratio = Math.min(availableWidth / this.imageMessage.getPicture().getWidth(), availableHeight
				/ this.imageMessage.getPicture().getHeight());
		double width = this.imageMessage.getPicture().getWidth() * ratio, height = this.imageMessage.getPicture().getHeight() * ratio;
		double left = (view.getWidth() - width) / 2, top = (view.getHeight() - height) / 2;

		LinearLayout baseViewLayout = new LinearLayout(getActivity().getApplicationContext());
		baseViewLayout.setOrientation(LinearLayout.VERTICAL);

		LinearLayout.LayoutParams imageViewLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		imageViewLayoutParams.gravity = Gravity.CENTER;
		imageViewLayoutParams.setMargins((int) Math.ceil(left), 0, (int) Math.ceil(left), 0);

		baseViewLayout.addView(showImageWithSize(ratio), imageViewLayoutParams);
		baseViewLayout.addView(showScreenButtonWithSize(ratio), imageViewLayoutParams);
		for (ImageView imageButtonView : showImageButtonsWithSize(ratio))
			baseViewLayout.addView(imageButtonView, imageViewLayoutParams);
		ImageView closeImageView = showCloseButtonWithSize(ratio);
		if (closeImageView != null)
			baseViewLayout.addView(closeImageView, imageViewLayoutParams);

		FrameLayout.LayoutParams baseViewLayoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
				FrameLayout.LayoutParams.MATCH_PARENT);
		baseViewLayoutParams.gravity = Gravity.CENTER;
		view.addView(baseViewLayout, baseViewLayoutParams);

		return view;
	}

	private ImageView showImageWithSize(final double ratio) {
		ImageView imageView = new ImageView(getActivity().getApplicationContext(), this.imageMessage.getPicture().getUrl());
		getActivity().getSupportLoaderManager().initLoader(loaderId++, null, imageView);
		return imageView;
	}

	private ImageView showScreenButtonWithSize(final double ratio) {
		ImageView imageView = new ImageView(getActivity().getApplicationContext(), this.imageMessage.getPicture().getUrl());
		imageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

			}
		});
		getActivity().getSupportLoaderManager().initLoader(loaderId++, null, imageView);
		return imageView;
	}

	private List<ImageView> showImageButtonsWithSize(final double ratio) {

		List<ImageButton> imageButtons = new ArrayList<ImageButton>();
		for (Button button : this.imageMessage.getButtons()) {
			if (button.getType() == Button.Type.image)
				imageButtons.add((ImageButton) button);
		}

		List<ImageView> imageButtonViews = new ArrayList<ImageView>();
		for (ImageButton imageButton : imageButtons) {

			double width = imageButton.getPicture().getWidth() * ratio;
			double height = imageButton.getPicture().getHeight() * ratio;
			ImageView imageView = new ImageView(getActivity().getApplicationContext(), imageButton.getPicture().getUrl());
			imageView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {

				}
			});
			getActivity().getSupportLoaderManager().initLoader(loaderId++, null, imageView);
			imageButtonViews.add(imageView);
		}

		return imageButtonViews;

	}

	private ImageView showCloseButtonWithSize(final double ratio) {

		CloseButton closeButton = null;
		for (Button button : this.imageMessage.getButtons()) {
			if (button.getType() == Button.Type.close)
				closeButton = (CloseButton) button;
		}

		if (closeButton == null)
			return null;

		ImageView imageView = new ImageView(getActivity().getApplicationContext(), closeButton.getPicture().getUrl());
		imageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

			}
		});
		getActivity().getSupportLoaderManager().initLoader(loaderId++, null, imageView);

		return imageView;
	}

}
