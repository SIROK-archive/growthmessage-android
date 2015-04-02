package com.growthbeat.message.model;

import org.json.JSONException;
import org.json.JSONObject;

import com.growthbeat.utils.JSONObjectUtils;

public class PlainMessage extends Message {

	private String caption;
	private String text;

	public PlainMessage() {
		super();
	}

	public PlainMessage(JSONObject jsonObject) {
		super(jsonObject);
	}

	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Override
	public JSONObject getJsonObject() {

		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("caption", getCaption());
			jsonObject.put("text", getText());
		} catch (JSONException e) {
			throw new IllegalArgumentException("Failed to get JSON.");
		}
		return jsonObject;
	}

	@Override
	public void setJsonObject(JSONObject jsonObject) {

		if (jsonObject == null)
			return;

		try {
			if (JSONObjectUtils.hasAndIsNotNull(jsonObject, "caption"))
				setCaption(jsonObject.getString("caption"));
			if (JSONObjectUtils.hasAndIsNotNull(jsonObject, "text"))
				setText(jsonObject.getString("text"));
		} catch (JSONException e) {
			throw new IllegalArgumentException("Failed to parse JSON.");
		}

	}

}
