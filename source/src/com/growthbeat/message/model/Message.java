package com.growthbeat.message.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.growthbeat.message.GrowthMessage;
import com.growthbeat.model.Model;
import com.growthbeat.utils.DateUtils;
import com.growthbeat.utils.JSONObjectUtils;

public class Message extends Model {

	private String id;
	private Date created;
	private String token;
	private String title;
	private String body;
	private ArrayList<Button> buttons;
	
	public Message() {
		super();
	}

	private Message(JSONObject jsonObject) {
		buttons = new ArrayList<Button>(); //
		setJsonObject(jsonObject);
	}

	public static Message find(String clientId, String credentialId) {

/*		Map<String, Object> params = new HashMap<String, Object>();
		if (clientId != null)
			params.put("clientId", clientId);
		if (credentialId != null)
			params.put("credentialId", credentialId);

		JSONObject jsonObject = GrowthMessage.getInstance().getHttpClient().get("1/messages", params);

		return new Message(jsonObject);*/
		String sample = getSampleJSON();
		try
		{
			JSONObject sampleJson = new JSONObject(sample);
			return new Message(sampleJson);
		}
		catch (Exception e)
		{
			Log.e("error", e.toString());
		}
		return null;
//		return
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	@Override
	public JSONObject getJsonObject() { //TODO

		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("id", getId());
			jsonObject.put("created", DateUtils.formatToDateTimeString(created));
		} catch (JSONException e) {
			return null;
		}

		return jsonObject;

	}

	@Override
	public void setJsonObject(JSONObject jsonObject) {

		if (jsonObject == null)
			return;

		try {
			if (JSONObjectUtils.hasAndIsNotNull(jsonObject, "id"))
				setId(jsonObject.getString("id"));
			if (JSONObjectUtils.hasAndIsNotNull(jsonObject, "created"))
				setCreated(DateUtils.parseFromDateTimeString(jsonObject.getString("created")));
			if (JSONObjectUtils.hasAndIsNotNull(jsonObject, "token"))
				setToken(jsonObject.getString("token"));
			if (JSONObjectUtils.hasAndIsNotNull(jsonObject, "title"))
				setTitle(jsonObject.getString("title"));
			if (JSONObjectUtils.hasAndIsNotNull(jsonObject, "body"))
				setBody(jsonObject.getString("body"));
			if (JSONObjectUtils.hasAndIsNotNull(jsonObject, "buttons"))
			{
				JSONArray array = jsonObject.getJSONArray("buttons");
	            for(int i = 0; i < array.length(); i++)
	            {
	                JSONObject jsonButton = array.getJSONObject(i);
	                Button button = new Button();
	                button.setJsonObject(jsonButton);
	                buttons.add(button);
	            }
			}
			
		} catch (JSONException e) {
			throw new IllegalArgumentException("Failed to parse JSON.");
		}

	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public ArrayList<Button> getButtons() {
		return buttons;
	}

	public void setButtons(ArrayList<Button> buttons) {
		this.buttons = buttons;
	}

	public static String getSampleJSON()
    {
        String sample = "{" +
                "  \"token\": \"1234567abcdefg\"," +
                "  \"title\": \"タイトル\"," +
                "  \"body\": \"本文のメッセージ\"," +
                "  \"buttons\": [" +
                "    {" +
                "      \"label\": \"ボタン1\"," +
                "      \"intent\": \"http://google.com\"," +
                "      \"event\": \"event-1\"" +
                "    }," +
                "    {" +
                "      \"label\": \"ボタン2\"," +
                "      \"intent\": \"http://google.com\"," +
                "      \"event\": \"event-2\"" +
                "    }," +
                "    {" +
                "      \"label\": \"キャンセル\"," +
                "      \"intent\": null," +
                "      \"event\": \"event-3\"" +
                "    }" +
                "  ]" +
                "}";
        return sample;
    }
	
}