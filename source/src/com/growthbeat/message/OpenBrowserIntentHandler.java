package com.growthbeat.message;

import java.util.HashMap;

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
		if (intent.getAction().equals("openBrowser"))
		{
			boolean error = false;
			try
			{
				HashMap<String, String> map = intent.getData();
				Uri uri = Uri.parse(map.get("url"));
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
