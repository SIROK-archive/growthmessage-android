package com.growthbeat.message.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;

import com.growthbeat.message.model.Button;
import com.growthbeat.message.model.CloseButton;
import com.growthbeat.message.model.ImageButton;
import com.growthbeat.message.model.ImageMessage;
import com.growthbeat.message.model.Message;

public class MessageImageDownloader implements LoaderCallbacks<Bitmap> {

	private LoaderManager loaderManager;
	private Context context;
	private Message message;
	private Callback callback;

	private List<String> urlStrings = new ArrayList<String>();
	private Map<String, Bitmap> images = new HashMap<String, Bitmap>();

	public MessageImageDownloader(LoaderManager loaderManager, Context context, Message message, Callback callback) {
		super();
		this.loaderManager = loaderManager;
		this.context = context;
		this.message = message;
		this.callback = callback;
	}

	public void download() {

		switch (message.getType()) {
		case image:
			download((ImageMessage) message);
			break;
		default:
			if (callback != null) {
				callback.failure();
				callback = null;
			}
			break;
		}

	}

	private void download(ImageMessage imageMessage) {

		if (imageMessage.getPicture().getUrl() != null) {
			urlStrings.add(imageMessage.getPicture().getUrl());
		}

		for (Button button : imageMessage.getButtons()) {
			switch (button.getType()) {
			case image:
				urlStrings.add(((ImageButton) button).getPicture().getUrl());
				break;
			case close:
				urlStrings.add(((CloseButton) button).getPicture().getUrl());
				break;
			default:
				continue;
			}
		}

		int loaderId = -1;
		for (String urlString : urlStrings) {
			Bundle bundle = new Bundle();
			bundle.putString("url", urlString);
			loaderManager.initLoader(loaderId++, bundle, this);
		}

	}

	@Override
	public Loader<Bitmap> onCreateLoader(int id, Bundle bundle) {
		Loader<Bitmap> loader = new ImageLoader(context, bundle.getString("url"));
		loader.forceLoad();
		return loader;
	}

	@Override
	public void onLoadFinished(Loader<Bitmap> loader, Bitmap bitmap) {

		if (bitmap == null) {
			if (callback != null) {
				callback.failure();
				callback = null;
			}
			return;
		}

		if (!(loader instanceof ImageLoader))
			return;
		String urlString = ((ImageLoader) loader).getUrlString();

		images.put(urlString, bitmap);
		urlStrings.remove(urlString);
		if (urlStrings.size() == 0) {
			if (callback != null) {
				callback.success(images);
			}
		}

	}

	@Override
	public void onLoaderReset(Loader<Bitmap> loader) {
		loader.reset();
	}

	public interface Callback {

		public void success(Map<String, Bitmap> images);

		public void failure();

	}

	private static class ImageLoader extends AsyncTaskLoader<Bitmap> {

		private String urlString;

		public ImageLoader(Context context, String urlString) {
			super(context);
			this.urlString = urlString;
		}

		@Override
		public Bitmap loadInBackground() {

			if (urlString != null) {
				HttpClient client = new DefaultHttpClient();

				HttpConnectionParams.setConnectionTimeout(client.getParams(), 10000);
				HttpConnectionParams.setSoTimeout(client.getParams(), 10000);

				HttpGet method = new HttpGet(urlString);
				HttpResponse response = null;
				try {
					response = client.execute(method);
					if (response.getStatusLine().getStatusCode() == 200) {
						HttpEntity entity = response.getEntity();
						if (entity != null) {
							return BitmapFactory.decodeStream(entity.getContent());
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return null;

		}

		@Override
		public void onCanceled(Bitmap bitmap) {
			if (bitmap == null)
				return;
			if (bitmap.isRecycled())
				return;
			bitmap.recycle();
			bitmap = null;
		}

		public String getUrlString() {
			return urlString;
		}

	}

}