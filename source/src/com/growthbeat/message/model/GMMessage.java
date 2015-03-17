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

public class GMMessage extends Model {

	private String id;
	private Date created;
	private String title;
	private String body;
	private ArrayList<GMButton> buttons;
	
	public GMMessage() {
		super();
		buttons = new ArrayList<GMButton>();
	}

	private GMMessage(JSONObject jsonObject) {
		buttons = new ArrayList<GMButton>();
		setJsonObject(jsonObject);
	}

	public static GMMessage find(String clientId, String credentialId) {

		Map<String, Object> params = new HashMap<String, Object>();
		if (clientId != null)
			params.put("clientId", clientId);
		if (credentialId != null)
			params.put("credentialId", credentialId);
		params.put("eventId", "Event:P5C3vzoLOEijnlVj:Default:Open");

//		JSONObject jsonObject = GrowthMessage.getInstance().getHttpClient().get("0/messages", params);
		JSONObject jsonObject = GrowthMessage.getInstance().getHttpClient().post("0/message", params);
		
		Log.d("json", jsonObject.toString());
		
		return new GMMessage(jsonObject);
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
			if (JSONObjectUtils.hasAndIsNotNull(jsonObject, "caption"))
				setTitle(jsonObject.getString("caption"));
			if (JSONObjectUtils.hasAndIsNotNull(jsonObject, "text"))
				setBody(jsonObject.getString("text"));
			if (JSONObjectUtils.hasAndIsNotNull(jsonObject, "buttons"))
			{
				JSONArray array = jsonObject.getJSONArray("buttons");
	            for(int i = 0; i < array.length(); i++)
	            {
	                JSONObject jsonButton = array.getJSONObject(i);
	                GMButton button = new GMButton();
	                button.setJsonObject(jsonButton);
	                buttons.add(button);
	            }
			}
		} catch (JSONException e) {
			throw new IllegalArgumentException("Failed to parse JSON.");
		}

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

	public ArrayList<GMButton> getButtons() {
		return buttons;
	}

	public void setButtons(ArrayList<GMButton> buttons) {
		this.buttons = buttons;
	}
}