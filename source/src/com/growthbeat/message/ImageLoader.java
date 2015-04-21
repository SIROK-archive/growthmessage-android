package com.growthbeat.message;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.content.AsyncTaskLoader;

public class ImageLoader {

	private final static ImageLoader instance = new ImageLoader();

	private Map<String, AsyncUrlImageLoader> cachedImages = new HashMap<String, AsyncUrlImageLoader>();

	public static ImageLoader getInstance() {
		return ImageLoader.instance;
	}

	public AsyncUrlImageLoader generateLoader(Context context, String url) {

		AsyncUrlImageLoader asyncUrlImageLoader = cachedImages.get(url);
		if (asyncUrlImageLoader == null) {
			asyncUrlImageLoader = new AsyncUrlImageLoader(context, url);
			cachedImages.put(url, asyncUrlImageLoader);
		}

		return asyncUrlImageLoader;

	}

	public static class AsyncUrlImageLoader extends AsyncTaskLoader<Bitmap> {

		private String url;
		private Bitmap bitmap;

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
				this.bitmap = BitmapFactory.decodeStream(connection.getInputStream());
				return bitmap;
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
			this.bitmap = bitmap;
			this.bitmap.isRecycled();
			this.bitmap = null;

		}

		public Bitmap getBitmap() {
			return this.bitmap;
		}

		public boolean needLoad() {
			return this.bitmap == null || (this.bitmap != null && this.bitmap.isRecycled());
		}

	}

}
