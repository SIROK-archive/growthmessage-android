package com.growthbeat.message.view;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;

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

		String url = new String(this.url);
		if (url != null) {
			HttpClient client = new DefaultHttpClient();
			
			HttpConnectionParams.setConnectionTimeout(client.getParams(), 10);
			HttpConnectionParams.setSoTimeout(client.getParams(), 10);
			
			HttpGet method = new HttpGet(url);
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

}