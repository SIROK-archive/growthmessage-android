package com.growthbeat.message;

import java.util.ArrayList;

import android.content.Context;
import android.os.Handler;

import com.growthbeat.CatchableThread;
import com.growthbeat.GrowthbeatCore;
import com.growthbeat.GrowthbeatException;
import com.growthbeat.Logger;
import com.growthbeat.Preference;
import com.growthbeat.http.GrowthbeatHttpClient;
import com.growthbeat.message.model.GMButton;
import com.growthbeat.message.model.GMIntent;
import com.growthbeat.message.model.GMMessage;

public class GrowthMessage {

	public static final String LOGGER_DEFAULT_TAG = "GrowthMessage";
	public static final String HTTP_CLIENT_DEFAULT_BASE_URL = "https://stg.message.growthbeat.com/";
	public static final String PREFERENCE_DEFAULT_FILE_NAME = "growthmessage-preferences";

	private static final GrowthMessage instance = new GrowthMessage();
	private final Logger logger = new Logger(LOGGER_DEFAULT_TAG);
	private final GrowthbeatHttpClient httpClient = new GrowthbeatHttpClient(HTTP_CLIENT_DEFAULT_BASE_URL);
	private final Preference preference = new Preference(PREFERENCE_DEFAULT_FILE_NAME);

	private String applicationId = null;
	private String credentialId = null;
	
//	GrowthMessageDelegate delegate;

    ArrayList<MessageHandler> messageHandlers;
    ArrayList<IntentHandler> intentHandlers;
    GrowthMessageDelegate delegate;
    
//    private String sample;
    
	private GrowthMessage() {
		super();
	}

	public static GrowthMessage getInstance() {
		return instance;
	}

	public void initialize(final Context context, final String applicationId, final String credentialId) {

		GrowthbeatCore.getInstance().initialize(context, applicationId, credentialId);

		this.applicationId = applicationId;
		this.credentialId = credentialId;
		this.preference.setContext(GrowthbeatCore.getInstance().getContext());

		messageHandlers = new ArrayList<MessageHandler>();
		messageHandlers.add(new BasicMassageHandler(context));
		intentHandlers = new ArrayList<IntentHandler>();
		intentHandlers.add(new OpenBrowserIntentHandler(context));
		delegate = new GrowthMessageDelegate() { //TODO sample
			@Override
			public boolean shouldShowMessage(GMMessage message) {
				return true;
			}
		};
		
		//this is for sample json.
/*		AssetManager assetManager = context.getResources().getAssets();
		InputStream is;
		try {
		  is = assetManager.open("message_sample.json");
		  sample = inputStreemToString(is);
		} catch (Exception e) {
		  e.printStackTrace();
		}*/
	}
	
	public void openMessageIfAvailable() {

		final Handler handler = new Handler();
		new Thread(new Runnable() {
			@Override
			public void run() {

				logger.info("Check message...");

				try {
					final GMMessage message = GMMessage.find(GrowthbeatCore.getInstance().waitClient().getId(), credentialId);
/*					JSONObject sampleJson = new JSONObject(sample);
					final GMMessage message = new GMMessage();
					message.setJsonObject(sampleJson);*/
					logger.info(String.format("Message is found. (id: %s)", message.getId()));
					handler.post(new Runnable() {
						@Override
						public void run() {
							openMessage(message);
						}
					});

				} catch (GrowthbeatException e) {
					logger.info(String.format("Message is not found.", e.getMessage()));
				}
			}
			
		}).start();
	}
	
	public void openMessage(GMMessage message)
	{
		if (delegate.shouldShowMessage(message))
		{
			for (MessageHandler handler : messageHandlers)
			{
				handler.handleMessage(message, this);
			}
		}
	}

	public String getApplicationId() {
		return applicationId;
	}

	public String getCredentialId() {
		return credentialId;
	}

	public Logger getLogger() {
		return logger;
	}

	public GrowthbeatHttpClient getHttpClient() {
		return httpClient;
	}

	public Preference getPreference() {
		return preference;
	}
	
	public void didSelectButton(GMButton button, GMMessage message)
	{
		handleIntent(button.getIntent());
	}

	private void handleIntent(GMIntent intent)
	{
		for (IntentHandler handler : intentHandlers)
		{
			handler.handleIntent(intent);
		}
	}
	
	private static class Thread extends CatchableThread {

		public Thread(Runnable runnable) {
			super(runnable);
		}

		@Override
		public void uncaughtException(java.lang.Thread thread, Throwable e) {
			String message = "Uncaught Exception: " + e.getClass().getName();
			if (e.getMessage() != null)
				message += "; " + e.getMessage();
			GrowthMessage.getInstance().getLogger().warning(message);
			e.printStackTrace();
		}

	}

}
