package com.growthbeat.message;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.os.Handler;

import com.growthbeat.CatchableThread;
import com.growthbeat.GrowthbeatCore;
import com.growthbeat.GrowthbeatException;
import com.growthbeat.Logger;
import com.growthbeat.Preference;
import com.growthbeat.analytics.GrowthAnalytics;
import com.growthbeat.http.GrowthbeatHttpClient;
import com.growthbeat.message.model.GMButton;
import com.growthbeat.message.model.GMIntent;
import com.growthbeat.message.model.GMMessage;

public class GrowthMessage {

	public static final String LOGGER_DEFAULT_TAG = "GrowthMessage";
	public static final String HTTP_CLIENT_DEFAULT_BASE_URL = "https://api.stg.message.growthbeat.com/";
	public static final String PREFERENCE_DEFAULT_FILE_NAME = "growthmessage-preferences";

	private static final GrowthMessage instance = new GrowthMessage();
	private final Logger logger = new Logger(LOGGER_DEFAULT_TAG);
	private final GrowthbeatHttpClient httpClient = new GrowthbeatHttpClient(HTTP_CLIENT_DEFAULT_BASE_URL);
	private final Preference preference = new Preference(PREFERENCE_DEFAULT_FILE_NAME);

	private String applicationId = null;
	private String credentialId = null;
	
    private ArrayList<MessageHandler> messageHandlers;
    private ArrayList<IntentHandler> intentHandlers;
    private GrowthMessageDelegate delegate;
    
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

	}
	
	public void openMessageIfAvailable() {

		final Handler handler = new Handler();
		new Thread(new Runnable() {
			@Override
			public void run() {

				logger.info("Check message...");

				try {
					final GMMessage message = GMMessage.find(GrowthbeatCore.getInstance().waitClient().getId(), credentialId);
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
				catch (Exception e) {
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
				if (handler.handleMessage(message, this))
				{
					Map<String, String> properties = new HashMap<String, String>();
					properties.put("taskId", message.getTask().getId());
					properties.put("messageId", message.getId());
					GrowthAnalytics.getInstance().track("Event:" + applicationId + "GrowthMessage:ShowMessage", properties);
				}
				else
				{
					//not handled by the handler
				}
			}
		}
		else
		{
			logger.info("Message is found. (id: " + message.getId()+ ")");
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

		Map<String, String> properties = new HashMap<String, String>();
		properties.put("taskId", message.getTask().getId());
		properties.put("messageId", message.getId());
		properties.put("buttonId", button.getId());
		GrowthAnalytics.getInstance().track("Event:" + applicationId + "GrowthMessage:SelectButton", properties);

	}

	private void handleIntent(GMIntent intent)
	{
		for (IntentHandler handler : intentHandlers)
		{
			handler.handleIntent(intent);
		}
	}

	public void setMessageHandlers(ArrayList<MessageHandler> messageHandlers)
	{
		this.messageHandlers = messageHandlers;
	}
	public void setIntentHandlers(ArrayList<IntentHandler> intentHandlers)
	{
		this.intentHandlers = intentHandlers;
	}
	public void setDelegate(GrowthMessageDelegate delegate)
	{
		this.delegate = delegate;
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
