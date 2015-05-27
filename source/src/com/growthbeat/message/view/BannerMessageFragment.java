package com.growthbeat.message.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.growthbeat.message.model.BannerMessage;

public class BannerMessageFragment extends Fragment {

	private FrameLayout baseLayout = null;
	private BannerMessage bannerMessage = null;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		Object message = getArguments().get("message");
		if (message == null || !(message instanceof BannerMessage))
			return null;

		this.bannerMessage = (BannerMessage) message;
		
		Log.w("", "############################");
		Log.w("", "" + bannerMessage.getButtons().size());
		Log.w("", "" + bannerMessage.getType());
		Log.w("", "" + bannerMessage.getBannerType());
		Log.w("", "" + bannerMessage.getCaption());
		Log.w("", "" + bannerMessage.getText());
		Log.w("", "" + bannerMessage.getPosition());
		Log.w("", "" + bannerMessage.getDuration());
		Log.w("", "############################");

//		DisplayMetrics displayMetrics = getResources().getDisplayMetrics();

//		double availableWidth = Math.min(imageMessage.getPicture().getWidth() * displayMetrics.density, displayMetrics.widthPixels * 0.85);
//		double availableHeight = Math.min(imageMessage.getPicture().getHeight() * displayMetrics.density,
//				displayMetrics.heightPixels * 0.85);
//
//		final double ratio = Math.min(availableWidth / imageMessage.getPicture().getWidth(), availableHeight
//				/ imageMessage.getPicture().getHeight());
//
//		int width = (int) (imageMessage.getPicture().getWidth() * ratio);
//		int height = (int) (imageMessage.getPicture().getHeight() * ratio);
//		int left = (int) ((displayMetrics.widthPixels - width) / 2);
//		int top = (int) ((displayMetrics.heightPixels - height) / 2);
//
//		final Rect rect = new Rect(left, top, width, height);
//
//		baseLayout = new FrameLayout(getActivity());
//		baseLayout.setBackgroundColor(Color.argb(128, 0, 0, 0));
//
//		progressBar = new ProgressBar(getActivity(), null, android.R.attr.progressBarStyleLarge);
//		FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(100, 100);
//		layoutParams.gravity = Gravity.CENTER;
//		baseLayout.addView(progressBar, layoutParams);
//
//		MessageImageDownloader.Callback callback = new MessageImageDownloader.Callback() {
//			@Override
//			public void success(Map<String, Bitmap> images) {
//				cachedImages = images;
//				progressBar.setVisibility(View.GONE);
//				showImage(baseLayout, rect);
//				showScreenButton(baseLayout, rect);
//				showImageButtons(baseLayout, rect, ratio);
//				showCloseButton(baseLayout, rect, ratio);
//			}
//
//			@Override
//			public void failure() {
//				if (!getActivity().isFinishing())
//					getActivity().finish();
//			}
//		};
//		MessageImageDownloader messageImageDonwloader = new MessageImageDownloader(getActivity().getSupportLoaderManager(), getActivity(),
//				imageMessage, callback);
//		messageImageDonwloader.download();

		return baseLayout;

	}

}
