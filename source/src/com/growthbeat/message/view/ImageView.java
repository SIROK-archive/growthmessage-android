package com.growthbeat.message.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;

import com.growthbeat.message.AsyncUrlImageLoader;

public class ImageView extends android.widget.ImageView implements LoaderCallbacks<Bitmap> {

	private String url;

	public ImageView(Context context, String url) {
		super(context);
	}

	@Override
	public Loader<Bitmap> onCreateLoader(int id, Bundle bundle) {
		return new AsyncUrlImageLoader(getContext(), this.url);
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
