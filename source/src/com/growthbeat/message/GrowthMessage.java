package com.growthbeat.message;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.os.Handler;

import com.growthbeat.CatchableThread;
import com.growthbeat.GrowthbeatCore;
import com.growthbeat.GrowthbeatException;
import com.growthbeat.Logger;
import com.growthbeat.Preference;
import com.growthbeat.analytics.EventHandler;
import com.growthbeat.analytics.GrowthAnalytics;
import com.growthbeat.http.GrowthbeatHttpClient;
import com.growthbeat.message.handler.MessageHandler;
import com.growthbeat.message.handler.PlainMassageHandler;
import com.growthbeat.message.model.Button;
import com.growthbeat.message.model.Message;

public class GrowthMessage {

	public static final String LOGGER_DEFAULT_TAG = "GrowthMessage";
	public static final String HTTP_CLIENT_DEFAULT_BASE_URL = "https://api.message.growthbeat.com/";
	public static final String PREFERENCE_DEFAULT_FILE_NAME = "growthmessage-preferences";

	private static final GrowthMessage instance = new GrowthMessage();
	private final Logger logger = new Logger(LOGGER_DEFAULT_TAG);
	private final GrowthbeatHttpClient httpClient = new GrowthbeatHttpClient(HTTP_CLIENT_DEFAULT_BASE_URL);
	private final Preference preference = new Preference(PREFERENCE_DEFAULT_FILE_NAME);

	private Context context = null;
	private String applicationId = null;
	private String credentialId = null;

	private List<? extends MessageHandler> messageHandlers;

	private GrowthMessage() {
		super();
	}

	public static GrowthMessage getInstance() {
		return instance;
	}

	public void initialize(final Context context, final String applicationId, final String credentialId) {

		GrowthbeatCore.getInstance().initialize(context, applicationId, credentialId);
		GrowthAnalytics.getInstance().initialize(context, applicationId, credentialId);

		this.context = context.getApplicationContext();
		this.applicationId = applicationId;
		this.credentialId = credentialId;
		this.preference.setContext(GrowthbeatCore.getInstance().getContext());

		GrowthAnalytics.getInstance().addEventHandler(new EventHandler() {
			@Override
			public void callback(String eventId, Map<String, String> properties) {
				if (eventId != null && eventId.startsWith("Event:" + applicationId + ":GrowthMessage"))
					return;
				recevieMessage(eventId);
			}
		});

		setMessageHandlers(Arrays.asList(new PlainMassageHandler(context)));

	}

	public void recevieMessage(final String eventId) {

		final Handler handler = new Handler();
		new Thread(new Runnable() {
			@Override
			public void run() {

				logger.info("Receive message...");

				try {

					final Message message = Message.receive(GrowthbeatCore.getInstance().waitClient().getId(), eventId, credentialId);
					logger.info(String.format("Message is received. (id: %s)", message.getId()));

					handler.post(new Runnable() {
						@Override
						public void run() {
							handleMessage(message);
						}
					});

				} catch (GrowthbeatException e) {
					logger.info(String.format("Message is not found.", e.getMessage()));
				}

			}

		}).start();

	}

	public void handleMessage(Message message) {

		for (MessageHandler messageHandler : messageHandlers) {
			if (!messageHandler.handle(message))
				continue;
			Map<String, String> properties = new HashMap<String, String>();
			properties.put("taskId", message.getTask().getId());
			properties.put("messageId", message.getId());
			GrowthAnalytics.getInstance().track("Event:" + applicationId + "GrowthMessage:ShowMessage", properties);
			break;
		}

	}

	public void didSelectButton(Button button, Message message) {

		GrowthbeatCore.getInstance().handleIntent(button.getIntent());

		Map<String, String> properties = new HashMap<String, String>();
		properties.put("taskId", message.getTask().getId());
		properties.put("messageId", message.getId());
		properties.put("intentId", button.getIntent().getId());
		GrowthAnalytics.getInstance().track("Event:" + applicationId + "GrowthMessage:SelectButton", properties);

	}

	public Context getContext() {
		return context;
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

	public void setMessageHandlers(List<? extends MessageHandler> messageHandlers) {
		this.messageHandlers = messageHandlers;
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
