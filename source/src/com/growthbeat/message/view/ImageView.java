package com.growthbeat.message.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;

import com.growthbeat.message.AsyncUrlImageLoader;

public class ImageView extends android.widget.ImageView implements LoaderCallbacks<Bitmap> {

	private String url;
	private double ratio;

	public ImageView(Context context, String url, double ratio) {
		super(context);
		this.url = url;
		this.ratio = ratio;
	}

	@Override
	public Loader<Bitmap> onCreateLoader(int id, Bundle bundle) {
		Loader<Bitmap> loader = new AsyncUrlImageLoader(getContext(), this.url);
		loader.forceLoad();
		return loader;
	}

	@Override
	public void onLoadFinished(Loader<Bitmap> loader, Bitmap bitmap) {
		bitmap.setDensity((int) this.ratio);
		setImageBitmap(bitmap);
	}

	@Override
	public void onLoaderReset(Loader<Bitmap> loader) {
		loader.reset();
	}

}
