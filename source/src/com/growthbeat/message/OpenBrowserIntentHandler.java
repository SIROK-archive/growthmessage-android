package com.growthbeat.message;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import com.growthbeat.message.model.GMIntent;

public class OpenBrowserIntentHandler implements IntentHandler {

	Context context;
	
	public OpenBrowserIntentHandler(Context context)
	{
		this.context = context;
	}
	
	@Override
	public boolean handleIntent(GMIntent intent) {
		if (intent.getType().equals("open_url"))
		{
			boolean error = false;
			try
			{
				Uri uri = Uri.parse(intent.getUrl());
				Intent i = new Intent(Intent.ACTION_VIEW, uri);
				i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				context.startActivity(i);
			}
			catch (Exception e)
			{
				Log.e("exception", e.toString());
				error = true;
			}
			finally
			{
				
			}
			return !error;
		}
		else
			return false;
	}

}
