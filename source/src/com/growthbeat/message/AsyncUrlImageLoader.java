package com.growthbeat.message;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.content.AsyncTaskLoader;

public class AsyncUrlImageLoader extends AsyncTaskLoader<Bitmap> {

	private String url;

	public AsyncUrlImageLoader(Context context, String url) {
		super(context);
		this.url = url;
	}

	@Override
	public Bitmap loadInBackground() {

		try {
			URL url = new URL(this.url);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoInput(true);
			connection.connect();
			return BitmapFactory.decodeStream(connection.getInputStream());
		} catch (IOException e) {
		}

		return null;
	}

	@Override
	public void onCanceled(Bitmap bitmap) {
		if (bitmap == null)
			return;
		if (bitmap.isRecycled())
			return;
		bitmap.isRecycled();
		bitmap = null;

	}

}