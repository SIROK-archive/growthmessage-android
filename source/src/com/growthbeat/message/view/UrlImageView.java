package com.growthbeat.message.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;

public class UrlImageView extends android.widget.ImageView implements LoaderCallbacks<Bitmap> {

	private String url;
	private Runnable successRun;
	private Runnable failureRun;

	public UrlImageView(Context context, String url, Runnable successRun, Runnable failureRun) {
		super(context);
		this.url = url;
		this.successRun = successRun;
		this.failureRun = failureRun;
	}

	@Override
	public Loader<Bitmap> onCreateLoader(int id, Bundle bundle) {
		Loader<Bitmap> loader = new AsyncUrlImageLoader(getContext(), this.url);
		loader.forceLoad();
		return loader;
	}

	@Override
	public void onLoadFinished(Loader<Bitmap> loader, Bitmap bitmap) {
		if(bitmap == null)
			failureRun.run();
		else
			successRun.run();
		setImageBitmap(bitmap);
	}

	@Override
	public void onLoaderReset(Loader<Bitmap> loader) {
		loader.reset();
	}

}
