package com.growthbeat.message.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.growthbeat.message.AsyncUrlImageLoader;

public class UrlImageButton extends android.widget.ImageView implements LoaderCallbacks<Bitmap> {

	private String url;

	public UrlImageButton(Context context, String url) {
		super(context);
		this.url = url;
		setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View view, MotionEvent event) {
				ImageView imageView = (ImageView) view;
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					imageView.setColorFilter(Color.argb(128, 0, 0, 0), PorterDuff.Mode.SRC_ATOP);
					imageView.invalidate();
					break;
				case MotionEvent.ACTION_UP:
				case MotionEvent.ACTION_CANCEL:
					imageView.getDrawable().clearColorFilter();
					imageView.invalidate();
					break;
				}
				return false;
			}
		});
	}

	@Override
	public Loader<Bitmap> onCreateLoader(int id, Bundle bundle) {
		Loader<Bitmap> loader = new AsyncUrlImageLoader(getContext(), this.url);
		loader.forceLoad();
		return loader;
	}

	@Override
	public void onLoadFinished(Loader<Bitmap> loader, Bitmap bitmap) {
		setImageBitmap(bitmap);
	}

	@Override
	public void onLoaderReset(Loader<Bitmap> loader) {
		loader.reset();
	}

}
