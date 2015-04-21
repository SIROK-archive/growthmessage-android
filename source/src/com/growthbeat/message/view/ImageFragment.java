package com.growthbeat.message.view;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.Loader;
import android.support.v4.content.Loader.OnLoadCompleteListener;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.growthbeat.message.ImageLoader;
import com.growthbeat.message.ImageLoader.AsyncUrlImageLoader;
import com.growthbeat.message.model.Button;
import com.growthbeat.message.model.CloseButton;
import com.growthbeat.message.model.ImageButton;
import com.growthbeat.message.model.ImageMessage;

public class ImageFragment extends Fragment {

	private ImageMessage imageMessage = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Object message = savedInstanceState.get("message");
		if (message == null)
			return;

		if (!(message instanceof ImageMessage))
			return;

		this.imageMessage = (ImageMessage) message;

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(android.R.id.content, null);

		double availableWidth = Math.min(this.imageMessage.getPicture().getWidth(), view.getWidth() * 0.75), availableHeight = Math.min(
				this.imageMessage.getPicture().getHeight(), view.getHeight() * 0.75);
		double ratio = Math.min(availableWidth / this.imageMessage.getPicture().getWidth(), availableHeight
				/ this.imageMessage.getPicture().getHeight());
		double width = this.imageMessage.getPicture().getWidth() * ratio, height = this.imageMessage.getPicture().getHeight() * ratio;
		double left = (view.getWidth() - width) / 2, top = (view.getHeight() - height) / 2;

		LinearLayout baseViewLayout = new LinearLayout(getActivity().getApplicationContext());
		baseViewLayout.setOrientation(LinearLayout.VERTICAL);
		LinearLayout.LayoutParams baseViewLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.MATCH_PARENT);
		baseViewLayoutParams.gravity = Gravity.CENTER;

		LinearLayout.LayoutParams imageViewLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		imageViewLayoutParams.gravity = Gravity.CENTER;
		imageViewLayoutParams.setMargins((int) Math.ceil(left), 0, (int) Math.ceil(left), 0);

		baseViewLayout.addView(showImageWithSize(ratio), imageViewLayoutParams);
		baseViewLayout.addView(showScreenButtonWithSize(ratio), imageViewLayoutParams);
		for (android.widget.ImageButton imageButtonView : showImageButtonsWithSize(ratio))
			baseViewLayout.addView(imageButtonView, imageViewLayoutParams);
		baseViewLayout.addView(showCloseButtonWithSize(ratio), imageViewLayoutParams);

		return baseViewLayout;
	}

	private ImageView showImageWithSize(final double ratio) {
		final ImageView imageView = new ImageView(getActivity().getApplicationContext());
		AsyncUrlImageLoader asyncImageLoader = ImageLoader.getInstance().generateLoader(getActivity().getApplicationContext(),
				this.imageMessage.getPicture().getUrl());
		if (asyncImageLoader.needLoad()) {
			asyncImageLoader.registerListener(1, new OnLoadCompleteListener<Bitmap>() {
				@Override
				public void onLoadComplete(Loader<Bitmap> loader, Bitmap bitmap) {
					imageView.setImageBitmap(bitmap);
				}
			});
			asyncImageLoader.startLoading();
		} else {
			imageView.setImageBitmap(asyncImageLoader.getBitmap());
		}

		return imageView;
	}

	private android.widget.ImageButton showScreenButtonWithSize(final double ratio) {
		final android.widget.ImageButton imageButtonView = new android.widget.ImageButton(getActivity().getApplicationContext());
		imageButtonView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

			}
		});
		AsyncUrlImageLoader asyncImageLoader = ImageLoader.getInstance().generateLoader(getActivity().getApplicationContext(),
				this.imageMessage.getPicture().getUrl());
		if (asyncImageLoader.needLoad()) {
			asyncImageLoader.registerListener(1, new OnLoadCompleteListener<Bitmap>() {
				@Override
				public void onLoadComplete(Loader<Bitmap> loader, Bitmap bitmap) {
					imageButtonView.setImageBitmap(bitmap);
				}
			});
			asyncImageLoader.startLoading();
		} else {
			imageButtonView.setImageBitmap(asyncImageLoader.getBitmap());
		}

		return imageButtonView;
	}

	private List<android.widget.ImageButton> showImageButtonsWithSize(final double ratio) {

		List<ImageButton> imageButtons = new ArrayList<ImageButton>();
		for (Button button : this.imageMessage.getButtons()) {
			if (button.getType() == Button.Type.image)
				imageButtons.add((ImageButton) button);
		}

		List<android.widget.ImageButton> imageButtonViews = new ArrayList<android.widget.ImageButton>();
		for (ImageButton imageButton : imageButtons) {

			double width = imageButton.getPicture().getWidth() * ratio;
			double height = imageButton.getPicture().getHeight() * ratio;
			final android.widget.ImageButton imageButtonView = new android.widget.ImageButton(getActivity().getApplicationContext());
			imageButtonView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

				}
			});
			AsyncUrlImageLoader asyncImageLoader = ImageLoader.getInstance().generateLoader(getActivity().getApplicationContext(),
					imageButton.getPicture().getUrl());
			if (asyncImageLoader.needLoad()) {
				asyncImageLoader.registerListener(1, new OnLoadCompleteListener<Bitmap>() {
					@Override
					public void onLoadComplete(Loader<Bitmap> loader, Bitmap bitmap) {
						imageButtonView.setImageBitmap(bitmap);
					}
				});
				asyncImageLoader.startLoading();
			} else {
				imageButtonView.setImageBitmap(asyncImageLoader.getBitmap());
			}

			imageButtonViews.add(imageButtonView);
		}

		return imageButtonViews;

	}

	private android.widget.ImageButton showCloseButtonWithSize(final double ratio) {

		CloseButton closeButton = null;
		for (Button button : this.imageMessage.getButtons()) {
			if (button.getType() == Button.Type.close)
				closeButton = (CloseButton) button;
		}

		final android.widget.ImageButton imageButtonView = new android.widget.ImageButton(getActivity().getApplicationContext());
		imageButtonView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

			}
		});
		AsyncUrlImageLoader asyncImageLoader = ImageLoader.getInstance().generateLoader(getActivity().getApplicationContext(),
				closeButton.getPicture().getUrl());
		if (asyncImageLoader.needLoad()) {
			asyncImageLoader.registerListener(1, new OnLoadCompleteListener<Bitmap>() {
				@Override
				public void onLoadComplete(Loader<Bitmap> loader, Bitmap bitmap) {
					imageButtonView.setImageBitmap(bitmap);
				}
			});
			asyncImageLoader.startLoading();
		} else {
			imageButtonView.setImageBitmap(asyncImageLoader.getBitmap());

		}

		return imageButtonView;
	}

}
