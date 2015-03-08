package com.growthbeat.message;

import android.content.Context;

import com.growthbeat.CatchableThread;
import com.growthbeat.GrowthbeatCore;
import com.growthbeat.GrowthbeatException;
import com.growthbeat.Logger;
import com.growthbeat.Preference;
import com.growthbeat.http.GrowthbeatHttpClient;
import com.growthbeat.message.model.Message;

public class GrowthMessage {

	public static final String LOGGER_DEFAULT_TAG = "GrowthMessage";
	public static final String HTTP_CLIENT_DEFAULT_BASE_URL = "https://api.message.growthbeat.com/";
	public static final String PREFERENCE_DEFAULT_FILE_NAME = "growthmessage-preferences";

	private static final GrowthMessage instance = new GrowthMessage();
	private final Logger logger = new Logger(LOGGER_DEFAULT_TAG);
	private final GrowthbeatHttpClient httpClient = new GrowthbeatHttpClient(HTTP_CLIENT_DEFAULT_BASE_URL);
	private final Preference preference = new Preference(PREFERENCE_DEFAULT_FILE_NAME);

	private String applicationId = null;
	private String credentialId = null;

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

		new Thread(new Runnable() {
			@Override
			public void run() {

				logger.info("Check message...");

				try {
					Message message = Message.find(GrowthbeatCore.getInstance().waitClient().getId(), credentialId);
					logger.info(String.format("Message is found. (id: %s)", message.getId()));
					// TODO Show message dialog
				} catch (GrowthbeatException e) {
					logger.info(String.format("Message is not found.", e.getMessage()));
				}

			}
		}).start();

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
